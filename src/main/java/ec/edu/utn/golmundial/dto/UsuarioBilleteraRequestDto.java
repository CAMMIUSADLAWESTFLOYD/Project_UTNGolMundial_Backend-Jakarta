package ec.edu.utn.golmundial.dto;

import java.io.Serializable;

// DTO para registrar un nuevo usuario con su billetera
public class UsuarioBilleteraRequestDto implements Serializable {

    private String username;
    private String nombre;
    private String email;
    private Long rolId;

    public UsuarioBilleteraRequestDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getRolId() {
        return rolId;
    }

    public void setRolId(Long rolId) {
        this.rolId = rolId;
    }
}
