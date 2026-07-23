package ec.edu.utn.golmundial.rest;

import ec.edu.utn.golmundial.dto.MensajeResponseDto;
import ec.edu.utn.golmundial.dto.PartidoSyncDto;
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
import java.util.logging.Level;
import java.util.logging.Logger;

// Recurso REST para la sincronizacion de partidos
@Path("/partidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PartidoResource {

    private static final Logger LOGGER = Logger.getLogger(PartidoResource.class.getName());

    @PersistenceContext(unitName = "UTNGolCoinPU")
    private EntityManager em;

    // Endpoint para registrar o actualizar el partido desde .NET
    @POST
    @Transactional
    public Response sincronizarPartido(PartidoSyncDto dto) {
        try {
            if (dto == null || dto.getId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new MensajeResponseDto("Datos de partido invalidos", false))
                        .build();
            }

            // Insercion o actualizacion en MariaDB respetando el ID de .NET
            String sql = "INSERT INTO partidos (id, seleccion_local_id, seleccion_visitante_id, nombre_local, nombre_visitante, fase_codigo, fase_nombre, estado, fecha_hora_utc, fecha_sync) " +
                         "VALUES (?, ?, ?, 'Local', 'Visitante', ?, 'Fase Regular', ?, ?, ?) " +
                         "ON DUPLICATE KEY UPDATE " +
                         "seleccion_local_id = VALUES(seleccion_local_id), " +
                         "seleccion_visitante_id = VALUES(seleccion_visitante_id), " +
                         "fase_codigo = VALUES(fase_codigo), " +
                         "estado = VALUES(estado), " +
                         "fecha_hora_utc = VALUES(fecha_hora_utc), " +
                         "fecha_sync = VALUES(fecha_sync)";

            em.createNativeQuery(sql)
              .setParameter(1, dto.getId())
              .setParameter(2, dto.getLocalId() != null ? dto.getLocalId() : 0)
              .setParameter(3, dto.getVisitanteId() != null ? dto.getVisitanteId() : 0)
              .setParameter(4, dto.getFaseCodigo() != null ? dto.getFaseCodigo() : "N/A")
              .setParameter(5, dto.getEstado() != null ? dto.getEstado() : "PROGRAMADO")
              .setParameter(6, dto.getFechaPartido() != null ? dto.getFechaPartido() : "2026-01-01 00:00:00")
              .setParameter(7, LocalDateTime.now())
              .executeUpdate();

            return Response.ok()
                           .entity(new MensajeResponseDto("Partido sincronizado exitosamente", true))
                           .build();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al sincronizar partido", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity(new MensajeResponseDto("Error interno al sincronizar", false))
                           .build();
        }
    }
}
