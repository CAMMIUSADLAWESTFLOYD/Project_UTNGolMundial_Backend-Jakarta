package ec.edu.utn.golmundial.dto;

import java.io.Serializable;
import java.math.BigDecimal;

// DTO para enviar la respuesta de la billetera al cliente
public class BilleteraResponseDto implements Serializable {

    private Long billeteraId;
    private Long usuarioId;
    private String username;
    private BigDecimal saldo;

    public BilleteraResponseDto() {
    }

    public BilleteraResponseDto(Long billeteraId, Long usuarioId, String username, BigDecimal saldo) {
        this.billeteraId = billeteraId;
        this.usuarioId = usuarioId;
        this.username = username;
        this.saldo = saldo;
    }

    public Long getBilleteraId() {
        return billeteraId;
    }

    public void setBilleteraId(Long billeteraId) {
        this.billeteraId = billeteraId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
}
