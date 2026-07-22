package ec.edu.utn.golmundial.rest;

import ec.edu.utn.golmundial.dto.ReporteResumenDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

// Recurso REST para la generacion de reportes
@Path("/reportes")
public class ReportesResource {

    private static final Logger LOGGER = Logger.getLogger(ReportesResource.class.getName());

    @PersistenceContext(unitName = "UTNGolCoinPU")
    private EntityManager em;

    // Endpoint para obtener el resumen general
    @GET
    @Path("/resumen")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerResumen() {
        ReporteResumenDto reporte = new ReporteResumenDto();
        
        try {
            // Inicializar valores por defecto
            reporte.setUtnGolCoinEnCirculacion(BigDecimal.ZERO);
            reporte.setPartidoMasPredicciones("");
            reporte.setTotalPredicciones(0L);
            reporte.setUsuariosRegistrados(0L);
            reporte.setPartidosFinalizados(0L);

            List<?> circulacionList = em.createNativeQuery("SELECT total_utngolcoin_circulante FROM vista_circulacion_monedas LIMIT 1").getResultList();
            if (!circulacionList.isEmpty() && circulacionList.get(0) != null) {
                reporte.setUtnGolCoinEnCirculacion(new BigDecimal(circulacionList.get(0).toString()));
            }
            
            List<?> partidoInfoList = em.createNativeQuery("SELECT * FROM vista_partidos_mas_predicciones LIMIT 1").getResultList();
            if (!partidoInfoList.isEmpty() && partidoInfoList.get(0) != null) {
                Object[] partidoInfo = (Object[]) partidoInfoList.get(0);
                if (partidoInfo.length >= 5) {
                    String local = partidoInfo[1] != null ? partidoInfo[1].toString() : "";
                    String visitante = partidoInfo[2] != null ? partidoInfo[2].toString() : "";
                    reporte.setPartidoMasPredicciones(local + " vs " + visitante);
                    reporte.setTotalPredicciones(partidoInfo[4] != null ? Long.valueOf(partidoInfo[4].toString()) : 0L);
                }
            }
            
            List<?> usuariosList = em.createNativeQuery("SELECT COUNT(*) FROM usuarios").getResultList();
            if (!usuariosList.isEmpty() && usuariosList.get(0) != null) {
                reporte.setUsuariosRegistrados(Long.valueOf(usuariosList.get(0).toString()));
            }
            
            List<?> partidosList = em.createNativeQuery("SELECT COUNT(*) FROM partidos WHERE estado = 'FINALIZADO'").getResultList();
            if (!partidosList.isEmpty() && partidosList.get(0) != null) {
                reporte.setPartidosFinalizados(Long.valueOf(partidosList.get(0).toString()));
            }
            
            return Response.ok(reporte).build();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al procesar el resumen", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("{\"error\":\"Ocurrio un error interno en el servidor\"}")
                           .build();
        }
    }
}
