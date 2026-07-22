package ec.edu.utn.golmundial.dao;

import ec.edu.utn.golmundial.modelos.Billetera;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

// DAO sin estado para gestionar las billeteras de usuarios
@Stateless
public class BilleteraDao {

    @PersistenceContext(unitName = "UTNGolCoinPU")
    private EntityManager em;

    // Buscar billetera por su ID principal
    public Billetera buscarPorId(Long id) {
        return em.find(Billetera.class, id);
    }

    // Buscar billetera por el ID del usuario propietario
    public Billetera buscarPorUsuarioId(Long usuarioId) {
        TypedQuery<Billetera> query = em.createQuery("SELECT b FROM Billetera b WHERE b.usuarioId = :usuarioId", Billetera.class);
        query.setParameter("usuarioId", usuarioId);
        List<Billetera> resultados = query.getResultList();
        if (resultados.isEmpty()) {
            return null;
        }
        return resultados.get(0);
    }

    // Listar todas las billeteras registradas ordenadas por saldo
    public List<Billetera> listarTodas() {
        return em.createQuery("SELECT b FROM Billetera b ORDER BY b.saldo DESC", Billetera.class).getResultList();
    }
}
