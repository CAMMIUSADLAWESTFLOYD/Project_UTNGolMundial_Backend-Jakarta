package ec.edu.utn.golmundial.rest;

import ec.edu.utn.golmundial.dao.BonoDiarioDao;
import ec.edu.utn.golmundial.dto.BonoDiarioRequestDto;
import ec.edu.utn.golmundial.dto.MensajeResponseDto;
import ec.edu.utn.golmundial.modelos.BonoDiario;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

// Recurso REST para la entrega del bono diario a los usuarios
@Path("/bonos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BonoDiarioResource {

    @Inject
    private BonoDiarioDao bonoDiarioDao;

    // Endpoint para reclamar u otorgar el bono diario llamando a sp_otorgar_bono_diario
    @POST
    @Path("/otorgar")
    public Response otorgarBono(BonoDiarioRequestDto dto) {
        try {
            if (dto == null || dto.getUsuarioId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new MensajeResponseDto("ID de usuario requerido", false))
                        .build();
            }

            bonoDiarioDao.otorgarBonoDiario(dto.getUsuarioId());

            return Response.ok(new MensajeResponseDto("Bono diario otorgado exitosamente", true)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new MensajeResponseDto("Error al otorgar bono diario: " + e.getMessage(), false))
                    .build();
        }
    }

    // Endpoint para consultar el historial de bonos de un usuario
    @GET
    @Path("/usuario/{usuarioId}")
    public Response listarPorUsuario(@PathParam("usuarioId") Long usuarioId) {
        List<BonoDiario> lista = bonoDiarioDao.listarPorUsuario(usuarioId);
        return Response.ok(lista).build();
    }
}
