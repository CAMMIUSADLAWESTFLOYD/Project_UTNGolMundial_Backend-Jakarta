package ec.edu.utn.golmundial.dao;

import ec.edu.utn.golmundial.modelos.Transaccion;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

// DAO sin estado para la consulta de transacciones registradas
@Stateless
public class TransaccionDao {

    @PersistenceContext(unitName = "UTNGolCoinPU")
    private EntityManager em;

    // Buscar transaccion por su ID
    public Transaccion buscarPorId(Long id) {
        return em.find(Transaccion.class, id);
    }

    // Listar las transacciones asociadas a una billetera
    public List<Transaccion> listarPorBilletera(Long billeteraId) {
        TypedQuery<Transaccion> query = em.createQuery("SELECT t FROM Transaccion t WHERE t.billeteraId = :billeteraId ORDER BY t.fecha DESC", Transaccion.class);
        query.setParameter("billeteraId", billeteraId);
        return query.getResultList();
    }
}
