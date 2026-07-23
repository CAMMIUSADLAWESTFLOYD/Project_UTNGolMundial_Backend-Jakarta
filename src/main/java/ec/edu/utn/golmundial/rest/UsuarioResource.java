package ec.edu.utn.golmundial.rest;

import ec.edu.utn.golmundial.dao.UsuarioDao;
import ec.edu.utn.golmundial.dto.MensajeResponseDto;
import ec.edu.utn.golmundial.dto.UsuarioRegistroDto;
import ec.edu.utn.golmundial.modelos.Usuario;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

// Recurso REST para la gestion de usuarios
@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    private static final Logger LOGGER = Logger.getLogger(UsuarioResource.class.getName());

    @PersistenceContext(unitName = "UTNGolCoinPU")
    private EntityManager em;

    @Inject
    private UsuarioDao usuarioDao;

    // Endpoint para obtener un usuario por ID
    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") Long id) {
        try {
            Usuario usuario = usuarioDao.buscarPorId(id);
            if (usuario == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new MensajeResponseDto("Usuario no encontrado", false))
                        .build();
            }
            return Response.ok(usuario).build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener usuario", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new MensajeResponseDto("Error interno al obtener usuario", false))
                    .build();
        }
    }

    // Endpoint para registrar un usuario y su billetera
    @POST
    @Path("/registrar")
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
            
            return Response.ok(new MensajeResponseDto("Usuario registrado exitosamente", true)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity(new MensajeResponseDto("Error interno al crear usuario: " + e.getMessage(), false))
                           .build();
        }
    }
}
