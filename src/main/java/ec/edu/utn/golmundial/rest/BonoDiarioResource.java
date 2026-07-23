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
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

// Recurso REST para la entrega del bono diario a los usuarios
@Path("/bonos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Bonos Diarios", description = "Endpoints para otorgamiento y consulta de bonos diarios")
public class BonoDiarioResource {

    private static final Logger LOGGER = Logger.getLogger(BonoDiarioResource.class.getName());

    @Inject
    private BonoDiarioDao bonoDiarioDao;

    // Endpoint para reclamar u otorgar el bono diario llamando a sp_otorgar_bono_diario
    @POST
    @Path("/otorgar")
    @Operation(summary = "Otorgar bono diario", description = "Otorga el bono diario al usuario llamando a sp_otorgar_bono_diario")
    @APIResponse(responseCode = "200", description = "Bono otorgado exitosamente")
    @APIResponse(responseCode = "400", description = "Peticion invalida")
    public Response otorgarBono(BonoDiarioRequestDto dto) {
        try {
            if (dto == null || dto.getUsuarioId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new MensajeResponseDto("ID de usuario requerido", false))
                        .build();
            }

            LocalDate fecha = (dto.getFechaSimulada() != null) ? dto.getFechaSimulada() : LocalDate.now();
            bonoDiarioDao.otorgarBonoDiario(dto.getUsuarioId(), fecha);

            return Response.ok(new MensajeResponseDto("Bono diario otorgado exitosamente", true)).build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al otorgar bono diario", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new MensajeResponseDto("Error interno al otorgar bono diario", false))
                    .build();
        }
    }

    // Endpoint para consultar el historial de bonos de un usuario
    @GET
    @Path("/usuario/{usuarioId}")
    @Operation(summary = "Listar bonos diarios de usuario", description = "Consulta el historial de bonos diarios recibidos por el usuario")
    public Response listarPorUsuario(@PathParam("usuarioId") Long usuarioId) {
        try {
            List<BonoDiario> lista = bonoDiarioDao.listarPorUsuario(usuarioId);
            return Response.ok(lista).build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al listar bonos diarios", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new MensajeResponseDto("Error interno al listar bonos", false))
                    .build();
        }
    }
}
