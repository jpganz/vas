package com.sigetel.web.domain;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;


public class Inconsistencias implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("fecha_inicio")
    private String fechaInicio;

    @JsonProperty("fecha_fin")
    private String fechaFin;

    @JsonProperty("proveedor")
    private String proveedor;

    @JsonProperty("producto")
    private String producto;

    @JsonProperty("cliente")
    private String cliente;

    @JsonProperty("anexo")
    private String anexco;

    @JsonProperty("status_as400")
    private String statusAs400;

    @JsonProperty("fecha_operacion_as400")
    private String fechaOperacionAs400;

    @JsonProperty("status_vas")
    private String statusVas;

    @JsonProperty("fecha_operacion_vas")
    private String fechaOperacionVas;

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

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getAnexco() {
        return anexco;
    }

    public void setAnexco(String anexco) {
        this.anexco = anexco;
    }

    public String getStatusAs400() {
        return statusAs400;
    }

    public void setStatusAs400(String statusAs400) {
        this.statusAs400 = statusAs400;
    }

    public String getFechaOperacionAs400() {
        return fechaOperacionAs400;
    }

    public void setFechaOperacionAs400(String fechaOperacionAs400) {
        this.fechaOperacionAs400 = fechaOperacionAs400;
    }

    public String getStatusVas() {
        return statusVas;
    }

    public void setStatusVas(String statusVas) {
        this.statusVas = statusVas;
    }

    public String getFechaOperacionVas() {
        return fechaOperacionVas;
    }

    public void setFechaOperacionVas(String fechaOperacionVas) {
        this.fechaOperacionVas = fechaOperacionVas;
    }

	@Override
	public String toString() {
		return this.getProveedor() + "," +
				this.getProducto() + "," +
				this.getCliente() + "," +
				this.getAnexco() + "," +
				this.getStatusAs400() + "," +
				this.getFechaOperacionAs400() + "," +
				this.getStatusVas() + "," +
				this.getFechaOperacionVas();
	}
    
    
}
