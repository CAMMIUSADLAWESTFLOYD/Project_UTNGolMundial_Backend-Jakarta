package ec.edu.utn.golmundial.dao;

import ec.edu.utn.golmundial.modelos.BonoDiario;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.time.LocalDate;

// DAO sin estado para gestionar el bono diario
@Stateless
public class BonoDiarioDao {

    @PersistenceContext(unitName = "UTNGolCoinPU")
    private EntityManager em;

    // Listar historial de bonos diarios otorgados a un usuario
    public List<BonoDiario> listarPorUsuario(Long usuarioId) {
        TypedQuery<BonoDiario> query = em.createQuery("SELECT b FROM BonoDiario b WHERE b.usuarioId = :usuarioId", BonoDiario.class);
        query.setParameter("usuarioId", usuarioId);
        return query.getResultList();
    }

    // Llama al procedimiento almacenado sp_otorgar_bono_diario
    public void otorgarBonoDiario(Long usuarioId, LocalDate fecha) {
        StoredProcedureQuery sp = em.createStoredProcedureQuery("sp_otorgar_bono_diario");
        sp.registerStoredProcedureParameter("p_usuario_id", Long.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter("p_fecha", java.sql.Date.class, ParameterMode.IN);
        
        sp.setParameter("p_usuario_id", usuarioId);
        sp.setParameter("p_fecha", java.sql.Date.valueOf(fecha));
        sp.execute();
    }
}
