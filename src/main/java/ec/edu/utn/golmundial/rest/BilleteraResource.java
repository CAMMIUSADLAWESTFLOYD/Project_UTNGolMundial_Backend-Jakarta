package ec.edu.utn.golmundial.rest;

import ec.edu.utn.golmundial.dao.BilleteraDao;
import ec.edu.utn.golmundial.dao.TransaccionDao;
import ec.edu.utn.golmundial.dao.UsuarioDao;
import ec.edu.utn.golmundial.dto.BilleteraResponseDto;
import ec.edu.utn.golmundial.dto.MensajeResponseDto;
import ec.edu.utn.golmundial.modelos.Billetera;
import ec.edu.utn.golmundial.modelos.Transaccion;
import ec.edu.utn.golmundial.modelos.Usuario;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

// Recurso REST para consultar saldo de billetera y transacciones
@Path("/billeteras")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Billeteras", description = "Endpoints para consultar saldos y transacciones")
public class BilleteraResource {

    private static final Logger LOGGER = Logger.getLogger(BilleteraResource.class.getName());

    @Inject
    private BilleteraDao billeteraDao;

    @Inject
    private UsuarioDao usuarioDao;

    @Inject
    private TransaccionDao transaccionDao;

    // Consultar el saldo de billetera dado el ID del usuario
    @GET
    @Path("/usuario/{usuarioId}")
    @Operation(summary = "Consultar billetera por usuario", description = "Retorna la billetera y el saldo del usuario")
    @APIResponse(responseCode = "200", description = "Billetera encontrada")
    @APIResponse(responseCode = "404", description = "Billetera no encontrada")
    public Response consultarPorUsuario(@PathParam("usuarioId") Long usuarioId) {
        try {
            Billetera billetera = billeteraDao.buscarPorUsuarioId(usuarioId);
            if (billetera == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new MensajeResponseDto("Billetera no encontrada para el usuario", false))
                        .build();
            }

            Usuario usuario = usuarioDao.buscarPorId(usuarioId);
            String username = (usuario != null) ? usuario.getUsername() : "";

            BilleteraResponseDto dto = new BilleteraResponseDto(
                    billetera.getId(),
                    billetera.getUsuarioId(),
                    username,
                    billetera.getSaldo()
            );

            return Response.ok(dto).build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al consultar billetera por usuario", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new MensajeResponseDto("Error interno al consultar billetera", false))
                    .build();
        }
    }

    // Consultar las transacciones registradas de una billetera
    @GET
    @Path("/{billeteraId}/transacciones")
    @Operation(summary = "Listar transacciones de billetera", description = "Obtiene el historial de transacciones de una billetera")
    public Response listarTransacciones(@PathParam("billeteraId") Long billeteraId) {
        try {
            Billetera billetera = billeteraDao.buscarPorId(billeteraId);
            if (billetera == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new MensajeResponseDto("Billetera no encontrada", false))
                        .build();
            }
            List<Transaccion> transacciones = transaccionDao.listarPorBilletera(billeteraId);
            return Response.ok(transacciones).build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al listar transacciones", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new MensajeResponseDto("Error interno al listar transacciones", false))
                    .build();
        }
    }

    // Obtener todas las billeteras (para el ranking)
    @GET
    @Operation(summary = "Listar todas las billeteras", description = "Retorna todas las billeteras ordenadas por saldo para el ranking")
    public Response listarTodas() {
        try {
            List<Billetera> billeteras = billeteraDao.listarTodas();
            List<BilleteraResponseDto> ranking = new ArrayList<>();
            for (Billetera b : billeteras) {
                Usuario u = usuarioDao.buscarPorId(b.getUsuarioId());
                String username = (u != null) ? u.getUsername() : "Desconocido";
                ranking.add(new BilleteraResponseDto(b.getId(), b.getUsuarioId(), username, b.getSaldo()));
            }
            return Response.ok(ranking).build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al listar todas las billeteras", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new MensajeResponseDto("Error interno al obtener billeteras", false))
                    .build();
        }
    }
}
