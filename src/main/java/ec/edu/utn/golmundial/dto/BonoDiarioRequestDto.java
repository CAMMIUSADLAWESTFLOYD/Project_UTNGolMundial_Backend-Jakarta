package ec.edu.utn.golmundial.dto;

import java.io.Serializable;

// DTO para solicitar el otorgamiento del bono diario
public class BonoDiarioRequestDto implements Serializable {

    private Long usuarioId;

    public BonoDiarioRequestDto() {
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
