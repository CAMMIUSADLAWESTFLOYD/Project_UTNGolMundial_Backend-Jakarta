package ec.edu.utn.golmundial.dao;

import ec.edu.utn.golmundial.modelos.Usuario;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.TypedQuery;
import java.util.List;

// DAO sin estado para gestionar usuarios y llamadas al procedimiento almacenado
@Stateless
public class UsuarioDao {

    @PersistenceContext(unitName = "UTNGolCoinPU")
    private EntityManager em;

    // Buscar un usuario por su ID
    public Usuario buscarPorId(Long id) {
        return em.find(Usuario.class, id);
    }

    // Buscar un usuario por su nombre de usuario
    public Usuario buscarPorUsername(String username) {
        TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.username = :username", Usuario.class);
        query.setParameter("username", username);
        List<Usuario> resultados = query.getResultList();
        if (resultados.isEmpty()) {
            return null;
        }
        return resultados.get(0);
    }

    // Llama al procedimiento almacenado sp_registrar_usuario_billetera
    public void registrarUsuarioBilletera(String username, String nombre, String email, Long rolId) {
        StoredProcedureQuery sp = em.createStoredProcedureQuery("sp_registrar_usuario_billetera");
        sp.registerStoredProcedureParameter("p_username", String.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter("p_nombre", String.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter("p_email", String.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter("p_rol_id", Long.class, ParameterMode.IN);

        sp.setParameter("p_username", username);
        sp.setParameter("p_nombre", nombre);
        sp.setParameter("p_email", email);
        sp.setParameter("p_rol_id", rolId);

        sp.execute();
    }
}
