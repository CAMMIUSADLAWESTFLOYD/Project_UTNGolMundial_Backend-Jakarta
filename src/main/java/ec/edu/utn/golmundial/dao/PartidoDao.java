package ec.edu.utn.golmundial.dao;

import ec.edu.utn.golmundial.modelos.Partido;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

// DAO sin estado para la consulta de partidos
@Stateless
public class PartidoDao {

    @PersistenceContext(unitName = "UTNGolCoinPU")
    private EntityManager em;

    // Buscar partido por su ID
    public Partido buscarPorId(Long id) {
        return em.find(Partido.class, id);
    }

    // Listar todos los partidos guardados en el sistema
    public List<Partido> listarTodos() {
        TypedQuery<Partido> query = em.createQuery("SELECT p FROM Partido p", Partido.class);
        return query.getResultList();
    }
}
