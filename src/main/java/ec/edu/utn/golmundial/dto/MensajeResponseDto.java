package ec.edu.utn.golmundial.dto;

import java.io.Serializable;

// DTO para enviar respuestas simples con mensaje y estado al cliente
public class MensajeResponseDto implements Serializable {

    private String mensaje;
    private Boolean exitoso;

    public MensajeResponseDto() {
    }

    public MensajeResponseDto(String mensaje, Boolean exitoso) {
        this.mensaje = mensaje;
        this.exitoso = exitoso;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Boolean getExitoso() {
        return exitoso;
    }

    public void setExitoso(Boolean exitoso) {
        this.exitoso = exitoso;
    }
}
