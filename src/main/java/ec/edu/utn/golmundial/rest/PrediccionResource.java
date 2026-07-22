package ec.edu.utn.golmundial.rest;

import ec.edu.utn.golmundial.dao.PrediccionDao;
import ec.edu.utn.golmundial.dto.LiquidacionRequestDto;
import ec.edu.utn.golmundial.dto.MensajeResponseDto;
import ec.edu.utn.golmundial.dto.PrediccionRequestDto;
import ec.edu.utn.golmundial.modelos.Prediccion;
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
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

// Recurso REST para gestionar las predicciones de los partidos
@Path("/predicciones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Predicciones", description = "Endpoints para registrar, liquidar y consultar predicciones")
public class PrediccionResource {

    @Inject
    private PrediccionDao prediccionDao;

    // Endpoint para registrar una nueva prediccion consumiendo el procedimiento almacenado
    @POST
    @Path("/registrar")
    @Operation(summary = "Registrar prediccion", description = "Registra una nueva prediccion llamando a sp_registrar_prediccion")
    @APIResponse(responseCode = "200", description = "Prediccion registrada exitosamente")
    @APIResponse(responseCode = "400", description = "Datos de entrada invalidos")
    public Response registrarPrediccion(PrediccionRequestDto dto) {
        try {
            if (dto == null || dto.getUsuarioId() == null || dto.getPartidoId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new MensajeResponseDto("Datos de prediccion incompletos", false))
                        .build();
            }

            prediccionDao.registrarPrediccion(
                    dto.getUsuarioId(),
                    dto.getPartidoId(),
                    dto.getTipoResultado(),
                    dto.getMonto()
            );

            return Response.ok(new MensajeResponseDto("Prediccion registrada con exito", true)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new MensajeResponseDto("Error al registrar prediccion: " + e.getMessage(), false))
                    .build();
        }
    }

    // Endpoint para liquidar los premios de un partido finalizado
    @POST
    @Path("/liquidar")
    @Operation(summary = "Liquidar predicciones de partido", description = "Liquida predicciones llamando a sp_liquidar_predicciones_partido")
    @APIResponse(responseCode = "200", description = "Liquidacion completada exitosamente")
    @APIResponse(responseCode = "400", description = "Datos incompletos")
    public Response liquidarPredicciones(LiquidacionRequestDto dto) {
        try {
            if (dto == null || dto.getPartidoId() == null || dto.getResultadoFinal() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new MensajeResponseDto("Datos de liquidacion incompletos", false))
                        .build();
            }

            prediccionDao.liquidarPrediccionesPartido(dto.getPartidoId(), dto.getResultadoFinal());

            return Response.ok(new MensajeResponseDto("Liquidacion procesada correctamente", true)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new MensajeResponseDto("Error al liquidar predicciones: " + e.getMessage(), false))
                    .build();
        }
    }

    // Endpoint para consultar las predicciones de un usuario especifico
    @GET
    @Path("/usuario/{usuarioId}")
    @Operation(summary = "Listar predicciones de usuario", description = "Obtiene la lista de predicciones realizadas por un usuario")
    public Response listarPorUsuario(@PathParam("usuarioId") Long usuarioId) {
        List<Prediccion> lista = prediccionDao.listarPorUsuario(usuarioId);
        return Response.ok(lista).build();
    }
}
