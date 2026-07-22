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

// Recurso REST para gestionar las predicciones de los partidos
@Path("/predicciones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PrediccionResource {

    @Inject
    private PrediccionDao prediccionDao;

    // Endpoint para registrar una nueva prediccion consumiendo el procedimiento almacenado
    @POST
    @Path("/registrar")
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
    public Response listarPorUsuario(@PathParam("usuarioId") Long usuarioId) {
        List<Prediccion> lista = prediccionDao.listarPorUsuario(usuarioId);
        return Response.ok(lista).build();
    }
}
