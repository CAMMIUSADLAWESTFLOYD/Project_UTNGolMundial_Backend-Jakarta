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
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

// Recurso REST para gestionar las predicciones de los partidos
@Path("/predicciones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PrediccionResource {

    private static final Logger LOGGER = Logger.getLogger(PrediccionResource.class.getName());

    @Inject
    private PrediccionDao prediccionDao;

    // Endpoint para registrar una nueva prediccion consumiendo el procedimiento almacenado
    @POST
    @Path("/registrar")
    public Response registrarPrediccion(PrediccionRequestDto dto) {
        try {
            if (dto == null || dto.getUsuarioId() == null || dto.getPartidoId() == null || dto.getMonto() == null || dto.getTipoResultado() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new MensajeResponseDto("Datos de prediccion incompletos", false))
                        .build();
            }

            if (dto.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new MensajeResponseDto("El monto de la prediccion debe ser mayor a 0", false))
                        .build();
            }

            String tipo = dto.getTipoResultado().toUpperCase();
            if (tipo.equals("LOCAL")) {
                tipo = "1";
            } else if (tipo.equals("EMPATE")) {
                tipo = "X";
            } else if (tipo.equals("VISITANTE")) {
                tipo = "2";
            }

            if (!tipo.equals("1") && !tipo.equals("X") && !tipo.equals("2")) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new MensajeResponseDto("El tipo de resultado debe ser Local, Empate o Visitante", false))
                        .build();
            }

            prediccionDao.registrarPrediccion(
                    dto.getUsuarioId(),
                    dto.getPartidoId(),
                    tipo,
                    dto.getMonto()
            );

            return Response.ok(new MensajeResponseDto("Prediccion registrada con exito", true)).build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al registrar prediccion", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new MensajeResponseDto("Error interno al registrar prediccion", false))
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

            String resultadoDB = dto.getResultadoFinal();
            if ("Local".equalsIgnoreCase(resultadoDB)) {
                resultadoDB = "1";
            } else if ("Visitante".equalsIgnoreCase(resultadoDB)) {
                resultadoDB = "2";
            } else if ("Empate".equalsIgnoreCase(resultadoDB)) {
                resultadoDB = "X";
            }

            prediccionDao.liquidarPrediccionesPartido(dto.getPartidoId(), resultadoDB);

            return Response.ok(new MensajeResponseDto("Liquidacion procesada correctamente", true)).build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al liquidar predicciones", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new MensajeResponseDto("Error interno al liquidar predicciones", false))
                    .build();
        }
    }

    // Endpoint para consultar las predicciones de un usuario especifico
    @GET
    @Path("/usuario/{usuarioId}")
    public Response listarPorUsuario(@PathParam("usuarioId") Long usuarioId) {
        try {
            List<Prediccion> lista = prediccionDao.listarPorUsuario(usuarioId);
            return Response.ok(lista).build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al listar predicciones de usuario", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new MensajeResponseDto("Error interno al obtener predicciones", false))
                    .build();
        }
    }
}
