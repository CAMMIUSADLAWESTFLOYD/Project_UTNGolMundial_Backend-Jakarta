package ec.edu.utn.golmundial.dao;

import ec.edu.utn.golmundial.modelos.Prediccion;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;

// DAO sin estado para gestionar predicciones y sus procedimientos almacenados
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

    // Llama al procedimiento almacenado sp_liquidar_predicciones_partido
    public void liquidarPrediccionesPartido(Long partidoId, String resultadoFinal) {
        StoredProcedureQuery sp = em.createStoredProcedureQuery("sp_liquidar_predicciones_partido");
        sp.registerStoredProcedureParameter("p_partido_id", Long.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter("p_resultado_final", String.class, ParameterMode.IN);

        sp.setParameter("p_partido_id", partidoId);
        sp.setParameter("p_resultado_final", resultadoFinal);

        sp.execute();
    }
}
