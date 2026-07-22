package ec.edu.utn.golmundial.rest;

import ec.edu.utn.golmundial.dto.UsuarioRegistroDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDateTime;

// Recurso REST para la gestion de usuarios
@Path("/usuarios")
public class UsuarioResource {

    @PersistenceContext(unitName = "UTNGolCoinPU")
    private EntityManager em;

    // Endpoint para registrar un usuario y su billetera
    @POST
    @Path("/registrar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response registrar(UsuarioRegistroDto dto) {
        try {
            em.createNativeQuery("CALL sp_registrar_usuario_billetera(?, ?, ?, ?, ?, ?)")
                .setParameter(1, dto.getId())
                .setParameter(2, dto.getUsername())
                .setParameter(3, dto.getNombre())
                .setParameter(4, dto.getEmail())
                .setParameter(5, dto.getRolId())
                .setParameter(6, LocalDateTime.now())
                .executeUpdate();
            
            return Response.ok("{\"mensaje\":\"Usuario registrado exitosamente\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("{\"error\":\"" + e.getMessage() + "\"}")
                           .build();
        }
    }
}
