package ec.edu.utn.golmundial.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

// Entidad JPA para la tabla partidos
@Entity
@Table(name = "partidos")
public class Partido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "seleccion_local_id")
    private Long seleccionLocalId;

    @Column(name = "seleccion_visitante_id")
    private Long seleccionVisitanteId;

    @Column(name = "nombre_local")
    private String nombreLocal;

    @Column(name = "nombre_visitante")
    private String nombreVisitante;

    @Column(name = "fase_codigo")
    private String faseCodigo;

    @Column(name = "fase_nombre")
    private String faseNombre;

    @Column(name = "estado")
    private String estado;

    @Column(name = "fecha_hora_utc")
    private LocalDateTime fechaHoraUtc;

    @Column(name = "cuota_local")
    private BigDecimal cuotaLocal;

    @Column(name = "cuota_empate")
    private BigDecimal cuotaEmpate;

    @Column(name = "cuota_visitante")
    private BigDecimal cuotaVisitante;

    @Column(name = "fecha_sync")
    private LocalDateTime fechaSync;

    public Partido() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSeleccionLocalId() {
        return seleccionLocalId;
    }

    public void setSeleccionLocalId(Long seleccionLocalId) {
        this.seleccionLocalId = seleccionLocalId;
    }

    public Long getSeleccionVisitanteId() {
        return seleccionVisitanteId;
    }

    public void setSeleccionVisitanteId(Long seleccionVisitanteId) {
        this.seleccionVisitanteId = seleccionVisitanteId;
    }

    public String getNombreLocal() {
        return nombreLocal;
    }

    public void setNombreLocal(String nombreLocal) {
        this.nombreLocal = nombreLocal;
    }

    public String getNombreVisitante() {
        return nombreVisitante;
    }

    public void setNombreVisitante(String nombreVisitante) {
        this.nombreVisitante = nombreVisitante;
    }

    public String getFaseCodigo() {
        return faseCodigo;
    }

    public void setFaseCodigo(String faseCodigo) {
        this.faseCodigo = faseCodigo;
    }

    public String getFaseNombre() {
        return faseNombre;
    }

    public void setFaseNombre(String faseNombre) {
        this.faseNombre = faseNombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaHoraUtc() {
        return fechaHoraUtc;
    }

    public void setFechaHoraUtc(LocalDateTime fechaHoraUtc) {
        this.fechaHoraUtc = fechaHoraUtc;
    }

    public BigDecimal getCuotaLocal() {
        return cuotaLocal;
    }

    public void setCuotaLocal(BigDecimal cuotaLocal) {
        this.cuotaLocal = cuotaLocal;
    }

    public BigDecimal getCuotaEmpate() {
        return cuotaEmpate;
    }

    public void setCuotaEmpate(BigDecimal cuotaEmpate) {
        this.cuotaEmpate = cuotaEmpate;
    }

    public BigDecimal getCuotaVisitante() {
        return cuotaVisitante;
    }

    public void setCuotaVisitante(BigDecimal cuotaVisitante) {
        this.cuotaVisitante = cuotaVisitante;
    }

    public LocalDateTime getFechaSync() {
        return fechaSync;
    }

    public void setFechaSync(LocalDateTime fechaSync) {
        this.fechaSync = fechaSync;
    }
}
