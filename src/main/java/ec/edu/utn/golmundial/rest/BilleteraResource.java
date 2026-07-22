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
import java.util.List;

// Recurso REST para consultar saldo de billetera y transacciones
@Path("/billeteras")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BilleteraResource {

    @Inject
    private BilleteraDao billeteraDao;

    @Inject
    private UsuarioDao usuarioDao;

    @Inject
    private TransaccionDao transaccionDao;

    // Consultar el saldo de billetera dado el ID del usuario
    @GET
    @Path("/usuario/{usuarioId}")
    public Response consultarPorUsuario(@PathParam("usuarioId") Long usuarioId) {
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
    }

    // Consultar las transacciones registradas de una billetera
    @GET
    @Path("/{billeteraId}/transacciones")
    public Response listarTransacciones(@PathParam("billeteraId") Long billeteraId) {
        List<Transaccion> transacciones = transaccionDao.listarPorBilletera(billeteraId);
        return Response.ok(transacciones).build();
    }
}
