package ec.edu.utn.golmundial.dao;

import ec.edu.utn.golmundial.modelos.Billetera;
import ec.edu.utn.golmundial.modelos.Prediccion;
import ec.edu.utn.golmundial.modelos.Transaccion;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.TypedQuery;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

// DAO sin estado para gestionar predicciones y su liquidacion
@Stateless
public class PrediccionDao {

    @PersistenceContext(unitName = "UTNGolCoinPU")
    private EntityManager em;

    // Buscar prediccion por su ID
    public Prediccion buscarPorId(Long id) {
        return em.find(Prediccion.class, id);
    }

    // Listar las predicciones realizadas por un usuario
    public List<Prediccion> listarPorUsuario(Long usuarioId) {
        TypedQuery<Prediccion> query = em.createQuery("SELECT p FROM Prediccion p WHERE p.usuarioId = :usuarioId", Prediccion.class);
        query.setParameter("usuarioId", usuarioId);
        return query.getResultList();
    }

    // Llama al procedimiento almacenado sp_registrar_prediccion
    public void registrarPrediccion(Long usuarioId, Long partidoId, String tipoResultado, BigDecimal monto) {
        em.flush(); // Sincroniza estado previo antes de llamar al stored procedure
        
        StoredProcedureQuery sp = em.createStoredProcedureQuery("sp_registrar_prediccion");
        sp.registerStoredProcedureParameter("p_usuario_id", Long.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter("p_partido_id", Long.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter("p_tipo_resultado", String.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter("p_monto", BigDecimal.class, ParameterMode.IN);

        sp.setParameter("p_usuario_id", usuarioId);
        sp.setParameter("p_partido_id", partidoId);
        sp.setParameter("p_tipo_resultado", tipoResultado);
        sp.setParameter("p_monto", monto);

        sp.execute();
    }

    // Liquida las predicciones de un partido comparando con el resultado real
    public void liquidarPrediccionesPartido(Long partidoId, String resultadoFinal) {
        TypedQuery<Prediccion> query = em.createQuery("SELECT p FROM Prediccion p WHERE p.partidoId = :partidoId AND p.estado = 'PENDIENTE'", Prediccion.class);
        query.setParameter("partidoId", partidoId);
        List<Prediccion> predicciones = query.getResultList();

        for (Prediccion p : predicciones) {
            p.setFechaLiquidacion(LocalDateTime.now());
            if (p.getTipoResultado().equals(resultadoFinal)) {
                p.setEstado("GANADA");
                
                BigDecimal premio = p.getMonto().multiply(p.getCuotaAplicada());
                
                TypedQuery<Billetera> bQuery = em.createQuery("SELECT b FROM Billetera b WHERE b.usuarioId = :usuarioId", Billetera.class);
                bQuery.setParameter("usuarioId", p.getUsuarioId());
                List<Billetera> billeteras = bQuery.getResultList();
                
                if (!billeteras.isEmpty()) {
                    Billetera b = billeteras.get(0);
                    b.setSaldo(b.getSaldo().add(premio));
                    b.setFechaActualizacion(LocalDateTime.now());
                    em.merge(b);
                    em.flush(); // Fuerza la actualizacion del saldo en la BD inmediatamente
                    
                    Transaccion t = new Transaccion();
                    t.setBilleteraId(b.getId());
                    t.setTipo("PREMIO_PREDICCION");
                    t.setMonto(premio);
                    t.setSaldoResultante(b.getSaldo());
                    t.setPrediccionId(p.getId());
                    t.setDescripcion("Premio por prediccion ganada partido #" + partidoId);
                    t.setFecha(LocalDateTime.now());
                    em.persist(t);
                    em.flush(); // Sincroniza la transaccion insertada en la BD
                }
            } else {
                p.setEstado("PERDIDA");
            }
            em.merge(p);
            em.flush(); // Sincroniza el estado de la prediccion final (GANADA o PERDIDA) en la BD
        }
    }
}
