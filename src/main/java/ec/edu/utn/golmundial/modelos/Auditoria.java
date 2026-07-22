package ec.edu.utn.golmundial.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

// Entidad JPA para la tabla auditoria
@Entity
@Table(name = "auditoria")
public class Auditoria implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "usuario_admin_id")
    private Long usuarioAdminId;

    @Column(name = "accion")
    private String accion;

    @Column(name = "tabla_afectada")
    private String tablaAfectada;

    @Column(name = "registro_id")
    private Long registroId;

    @Column(name = "datos_anteriores")
    private String datosAnteriores;

    @Column(name = "datos_nuevos")
    private String datosNuevos;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    public Auditoria() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioAdminId() {
        return usuarioAdminId;
    }

    public void setUsuarioAdminId(Long usuarioAdminId) {
        this.usuarioAdminId = usuarioAdminId;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getTablaAfectada() {
        return tablaAfectada;
    }

    public void setTablaAfectada(String tablaAfectada) {
        this.tablaAfectada = tablaAfectada;
    }

    public Long getRegistroId() {
        return registroId;
    }

    public void setRegistroId(Long registroId) {
        this.registroId = registroId;
    }

    public String getDatosAnteriores() {
        return datosAnteriores;
    }

    public void setDatosAnteriores(String datosAnteriores) {
        this.datosAnteriores = datosAnteriores;
    }

    public String getDatosNuevos() {
        return datosNuevos;
    }

    public void setDatosNuevos(String datosNuevos) {
        this.datosNuevos = datosNuevos;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
