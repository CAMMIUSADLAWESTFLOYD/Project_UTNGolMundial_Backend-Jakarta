package ec.edu.utn.golmundial.dto;

import java.io.Serializable;

// DTO para sincronizar partidos desde el backend de .NET
public class PartidoSyncDto implements Serializable {

    private Integer id;
    private String faseCodigo;
    private String grupoCodigo;
    private String fechaPartido;
    private Integer localId;
    private Integer visitanteId;
    private Integer golesLocal;
    private Integer golesVisitante;
    private String estado;

    public PartidoSyncDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFaseCodigo() {
        return faseCodigo;
    }

    public void setFaseCodigo(String faseCodigo) {
        this.faseCodigo = faseCodigo;
    }

    public String getGrupoCodigo() {
        return grupoCodigo;
    }

    public void setGrupoCodigo(String grupoCodigo) {
        this.grupoCodigo = grupoCodigo;
    }

    public String getFechaPartido() {
        return fechaPartido;
    }

    public void setFechaPartido(String fechaPartido) {
        this.fechaPartido = fechaPartido;
    }

    public Integer getLocalId() {
        return localId;
    }

    public void setLocalId(Integer localId) {
        this.localId = localId;
    }

    public Integer getVisitanteId() {
        return visitanteId;
    }

    public void setVisitanteId(Integer visitanteId) {
        this.visitanteId = visitanteId;
    }

    public Integer getGolesLocal() {
        return golesLocal;
    }

    public void setGolesLocal(Integer golesLocal) {
        this.golesLocal = golesLocal;
    }

    public Integer getGolesVisitante() {
        return golesVisitante;
    }

    public void setGolesVisitante(Integer golesVisitante) {
        this.golesVisitante = golesVisitante;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
