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

// Recurso REST para la generacion de reportes
@Path("/reportes")
public class ReportesResource {

    @PersistenceContext(unitName = "UTNGolCoinPU")
    private EntityManager em;

    // Endpoint para obtener el resumen general
    @GET
    @Path("/resumen")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerResumen() {
        ReporteResumenDto reporte = new ReporteResumenDto();
        
        try {
            Object circulacionObj = em.createNativeQuery("SELECT * FROM vista_circulacion_monedas LIMIT 1").getSingleResult();
            if (circulacionObj != null) {
                reporte.setUtnGolCoinEnCirculacion(new BigDecimal(circulacionObj.toString()));
            }
            
            Object[] partidoInfo = (Object[]) em.createNativeQuery("SELECT * FROM vista_partidos_mas_predicciones LIMIT 1").getSingleResult();
            if (partidoInfo != null && partidoInfo.length >= 2) {
                reporte.setPartidoMasPredicciones(partidoInfo[0] != null ? partidoInfo[0].toString() : "");
                reporte.setTotalPredicciones(partidoInfo[1] != null ? Long.valueOf(partidoInfo[1].toString()) : 0L);
            }
            
            Object usuariosObj = em.createNativeQuery("SELECT COUNT(*) FROM usuarios").getSingleResult();
            if (usuariosObj != null) {
                reporte.setUsuariosRegistrados(Long.valueOf(usuariosObj.toString()));
            }
            
            Object partidosObj = em.createNativeQuery("SELECT COUNT(*) FROM partidos WHERE estado = 'FINALIZADO'").getSingleResult();
            if (partidosObj != null) {
                reporte.setPartidosFinalizados(Long.valueOf(partidosObj.toString()));
            }
            
            return Response.ok(reporte).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
