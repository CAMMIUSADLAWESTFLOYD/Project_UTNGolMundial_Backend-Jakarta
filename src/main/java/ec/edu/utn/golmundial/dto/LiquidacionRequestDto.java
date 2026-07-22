package ec.edu.utn.golmundial.dto;

import java.io.Serializable;

// DTO para recibir la solicitud de liquidacion de predicciones de un partido
public class LiquidacionRequestDto implements Serializable {

    private Long partidoId;
    private String resultadoFinal;

    public LiquidacionRequestDto() {
    }

    public Long getPartidoId() {
        return partidoId;
    }

    public void setPartidoId(Long partidoId) {
        this.partidoId = partidoId;
    }

    public String getResultadoFinal() {
        return resultadoFinal;
    }

    public void setResultadoFinal(String resultadoFinal) {
        this.resultadoFinal = resultadoFinal;
    }
}
