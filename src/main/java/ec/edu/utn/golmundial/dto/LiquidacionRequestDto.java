package ec.edu.utn.golmundial.dto;

import java.io.Serializable;

// DTO para recibir la solicitud de liquidacion de predicciones de un partido
public class LiquidacionRequestDto implements Serializable {

    private Long partidoId;
    private Integer golesLocal;
    private Integer golesVisitante;
    private String resultado;

    public LiquidacionRequestDto() {
    }

    public Long getPartidoId() {
        return partidoId;
    }

    public void setPartidoId(Long partidoId) {
        this.partidoId = partidoId;
    }

    public Integer getGolesLocal() {
        return golesLocal;
    }

    public void setGolesLocal(Integer golesLocal) {
        this.golesLocal = golesLocal;
    }

    public Integer getGolesVisitante() {
        return golesVisitante;
    }

    public void setGolesVisitante(Integer golesVisitante) {
        this.golesVisitante = golesVisitante;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}
