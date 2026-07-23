package ec.edu.utn.golmundial.dto;

import java.io.Serializable;

// DTO para sincronizar partidos desde .NET
public class PartidoSyncDto implements Serializable {

    private Integer id;
    private Integer numeroPartidoFifa;
    private String fechaPartido;
    private String estado;
    private Integer golesLocal;
    private Integer golesVisitante;
    private String faseCodigo;
    private String grupoCodigo;
    private Integer sedeId;
    private Integer localId;
    private Integer visitanteId;
    private String nombreLocal;
    private String nombreVisitante;

    public PartidoSyncDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumeroPartidoFifa() {
        return numeroPartidoFifa;
    }

    public void setNumeroPartidoFifa(Integer numeroPartidoFifa) {
        this.numeroPartidoFifa = numeroPartidoFifa;
    }

    public String getFechaPartido() {
        return fechaPartido;
    }

    public void setFechaPartido(String fechaPartido) {
        this.fechaPartido = fechaPartido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public Integer getSedeId() {
        return sedeId;
    }

    public void setSedeId(Integer sedeId) {
        this.sedeId = sedeId;
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
}
