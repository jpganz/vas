package com.sigetel.web.domain;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;


public class BillingInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("monto_plan")
    private String montoPlan;

    @JsonProperty("numero_cliente")
    private String numeroCliente;

    @JsonProperty("numero_anexo")
    private String numeroAnexo;

    @JsonProperty("nombre_cliente")
    private String nombreCliente;

    @JsonProperty("numero_telefono")
    private String numeroTelefono;

    @JsonProperty("estado")
    private String estado;

    @JsonProperty("fecha_estado")
    private String fechaEstado;

    @JsonProperty("codigo_servicio")
    private String codigoServicio;

    @JsonProperty("confirmation_event")
    private String confirmacionEvent;

    public String getMontoPlan() {
        return montoPlan;
    }

    public void setMontoPlan(String montoPlan) {
        this.montoPlan = montoPlan;
    }

    public String getNumeroCliente() {
        return numeroCliente;
    }

    public void setNumeroCliente(String numeroCliente) {
        this.numeroCliente = numeroCliente;
    }

    public String getNumeroAnexo() {
        return numeroAnexo;
    }

    public void setNumeroAnexo(String numeroAnexo) {
        this.numeroAnexo = numeroAnexo;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaEstado() {
        return fechaEstado;
    }

    public void setFechaEstado(String fechaEstado) {
        this.fechaEstado = fechaEstado;
    }

    public String getCodigoServicio() {
        return codigoServicio;
    }

    public void setCodigoServicio(String codigoServicio) {
        this.codigoServicio = codigoServicio;
    }

    public String getConfirmacionEvent() {
        return confirmacionEvent;
    }

    public void setConfirmacionEvent(String confirmacionEvent) {
        this.confirmacionEvent = confirmacionEvent;
    }

	@Override
	public String toString() {
		
		return this.getMontoPlan() + "," +
				this.getNumeroCliente() + "," +
				this.getNumeroAnexo() + "," +
				this.getNombreCliente() + "," +
				this.getNumeroTelefono() + "," +
				this.getEstado() + "," +
				this.getFechaEstado() + "," +
				this.getCodigoServicio() + "," +
				this.getConfirmacionEvent();
	}
    
    
}
