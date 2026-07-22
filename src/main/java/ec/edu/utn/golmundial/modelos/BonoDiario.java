package ec.edu.utn.golmundial.modelos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

// Entidad JPA para la tabla bonos_diarios
@Entity
@Table(name = "bonos_diarios")
public class BonoDiario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "monto")
    private BigDecimal monto;

    @Column(name = "transaccion_id")
    private Long transaccionId;

    @Column(name = "fecha_otorgado")
    private LocalDateTime fechaOtorgado;

    public BonoDiario() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Long getTransaccionId() {
        return transaccionId;
    }

    public void setTransaccionId(Long transaccionId) {
        this.transaccionId = transaccionId;
    }

    public LocalDateTime getFechaOtorgado() {
        return fechaOtorgado;
    }

    public void setFechaOtorgado(LocalDateTime fechaOtorgado) {
        this.fechaOtorgado = fechaOtorgado;
    }
}
