package ec.edu.utn.golmundial.dto;

// DTO para el registro de nuevos usuarios
public class UsuarioRegistroDto {

    private Integer id;
    private String username;
    private String nombre;
    private String email;
    private Short rolId;

    public UsuarioRegistroDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Short getRolId() {
        return rolId;
    }

    public void setRolId(Short rolId) {
        this.rolId = rolId;
    }
}
