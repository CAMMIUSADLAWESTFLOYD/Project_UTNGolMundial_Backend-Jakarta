package ec.edu.utn.golmundial.dto;

import java.math.BigDecimal;

// DTO para el resumen de reportes
public class ReporteResumenDto {

    private String partidoMasPredicciones;
    private Long totalPredicciones;
    private BigDecimal utnGolCoinEnCirculacion;
    private Long usuariosRegistrados;
    private Long partidosFinalizados;

    public ReporteResumenDto() {
    }

    public String getPartidoMasPredicciones() {
        return partidoMasPredicciones;
    }

    public void setPartidoMasPredicciones(String partidoMasPredicciones) {
        this.partidoMasPredicciones = partidoMasPredicciones;
    }

    public Long getTotalPredicciones() {
        return totalPredicciones;
    }

    public void setTotalPredicciones(Long totalPredicciones) {
        this.totalPredicciones = totalPredicciones;
    }

    public BigDecimal getUtnGolCoinEnCirculacion() {
        return utnGolCoinEnCirculacion;
    }

    public void setUtnGolCoinEnCirculacion(BigDecimal utnGolCoinEnCirculacion) {
        this.utnGolCoinEnCirculacion = utnGolCoinEnCirculacion;
    }

    public Long getUsuariosRegistrados() {
        return usuariosRegistrados;
    }

    public void setUsuariosRegistrados(Long usuariosRegistrados) {
        this.usuariosRegistrados = usuariosRegistrados;
    }

    public Long getPartidosFinalizados() {
        return partidosFinalizados;
    }

    public void setPartidosFinalizados(Long partidosFinalizados) {
        this.partidosFinalizados = partidosFinalizados;
    }
}
