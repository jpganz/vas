package com.sigetel.web.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Conciliacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("fecha_inicio")
    private String fechaInicio;

    @JsonProperty("fecha_fin")
    private String fechaFin;

    @JsonProperty("proveedor")
    private String proveedor;

    @JsonProperty("producto")
    private String producto;

    @JsonProperty("altas_as400")
    private String altasAs400;

    @JsonProperty("altas_ovas")
    private String altasOvas;

    @JsonProperty("bajas_as400")
    private String bajasAs400;

    @JsonProperty("bajas_ovas")
    private String bajasOvas;

    @JsonProperty("suspencion_as400")
    private String suspencionAs400;

    @JsonProperty("suspencion_ovas")
    private String suspencionOvas;

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getAltasAs400() {
        return altasAs400;
    }

    public void setAltasAs400(String altasAs400) {
        this.altasAs400 = altasAs400;
    }

    public String getAltasOvas() {
        return altasOvas;
    }

    public void setAltasOvas(String altasOvas) {
        this.altasOvas = altasOvas;
    }

    public String getBajasAs400() {
        return bajasAs400;
    }

    public void setBajasAs400(String bajasAs400) {
        this.bajasAs400 = bajasAs400;
    }

    public String getBajasOvas() {
        return bajasOvas;
    }

    public void setBajasOvas(String bajasOvas) {
        this.bajasOvas = bajasOvas;
    }

    public String getSuspencionAs400() {
        return suspencionAs400;
    }

    public void setSuspencionAs400(String suspencionAs400) {
        this.suspencionAs400 = suspencionAs400;
    }

    public String getSuspencionOvas() {
        return suspencionOvas;
    }

    public void setSuspencionOvas(String suspencionOvas) {
        this.suspencionOvas = suspencionOvas;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

	@Override
	public String toString() {	
		return this.getProveedor() + "," +
				this.getProducto() + "," +
				this.getAltasAs400() + "," +
				this.getAltasOvas() + "," +
				this.getBajasAs400() + "," +
				this.getBajasOvas() + "," +
				this.getSuspencionAs400() + "," +
				this.getSuspencionOvas();
	}
}
