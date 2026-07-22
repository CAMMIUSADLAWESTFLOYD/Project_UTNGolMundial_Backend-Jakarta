package ec.edu.utn.golmundial.rest;

import ec.edu.utn.golmundial.dao.UsuarioDao;
import ec.edu.utn.golmundial.dto.MensajeResponseDto;
import ec.edu.utn.golmundial.dto.UsuarioBilleteraRequestDto;
import ec.edu.utn.golmundial.modelos.Usuario;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

// Recurso REST para la gestion de usuarios
@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    private UsuarioDao usuarioDao;

    // Endpoint para registrar un usuario y su billetera llamando a sp_registrar_usuario_billetera
    @POST
    @Path("/registrar")
    public Response registrarUsuario(UsuarioBilleteraRequestDto dto) {
        try {
            if (dto == null || dto.getUsername() == null || dto.getEmail() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new MensajeResponseDto("Datos de usuario incompletos", false))
                        .build();
            }

            usuarioDao.registrarUsuarioBilletera(
                    dto.getUsername(),
                    dto.getNombre(),
                    dto.getEmail(),
                    dto.getRolId()
            );

            return Response.ok(new MensajeResponseDto("Usuario y billetera registrados exitosamente", true)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new MensajeResponseDto("Error al registrar usuario: " + e.getMessage(), false))
                    .build();
        }
    }

    // Endpoint para buscar usuario por su ID
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        Usuario usuario = usuarioDao.buscarPorId(id);
        if (usuario == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new MensajeResponseDto("Usuario no encontrado", false))
                    .build();
        }
        return Response.ok(usuario).build();
    }
}
