package ec.edu.utn.golmundial.dto;

import java.io.Serializable;

// DTO para solicitar el otorgamiento del bono diario
public class BonoDiarioRequestDto implements Serializable {

    private Long usuarioId;
    private java.time.LocalDate fechaSimulada;

    public BonoDiarioRequestDto() {
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public java.time.LocalDate getFechaSimulada() {
        return fechaSimulada;
    }

    public void setFechaSimulada(java.time.LocalDate fechaSimulada) {
        this.fechaSimulada = fechaSimulada;
    }
}
