package ec.edu.utn.golmundial.dto;

import java.io.Serializable;
import java.math.BigDecimal;

// DTO para recibir la solicitud de registro de una prediccion
public class PrediccionRequestDto implements Serializable {

    private Long usuarioId;
    private Long partidoId;
    private String tipoResultado;
    private BigDecimal monto;

    public PrediccionRequestDto() {
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getPartidoId() {
        return partidoId;
    }

    public void setPartidoId(Long partidoId) {
        this.partidoId = partidoId;
    }

    public String getTipoResultado() {
        return tipoResultado;
    }

    public void setTipoResultado(String tipoResultado) {
        this.tipoResultado = tipoResultado;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
}
