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

// Entidad JPA para la tabla transacciones
@Entity
@Table(name = "transacciones")
public class Transaccion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "billetera_id")
    private Long billeteraId;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "monto")
    private BigDecimal monto;

    @Column(name = "saldo_resultante")
    private BigDecimal saldoResultante;

    @Column(name = "prediccion_id")
    private Long prediccionId;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    public Transaccion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBilleteraId() {
        return billeteraId;
    }

    public void setBilleteraId(Long billeteraId) {
        this.billeteraId = billeteraId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public BigDecimal getSaldoResultante() {
        return saldoResultante;
    }

    public void setSaldoResultante(BigDecimal saldoResultante) {
        this.saldoResultante = saldoResultante;
    }

    public Long getPrediccionId() {
        return prediccionId;
    }

    public void setPrediccionId(Long prediccionId) {
        this.prediccionId = prediccionId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
