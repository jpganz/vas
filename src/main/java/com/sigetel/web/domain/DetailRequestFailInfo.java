package com.sigetel.web.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DetailRequestFailInfo implements Serializable{
	
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
	private String anexo;
	
	@JsonProperty("operacion_a_realizar")
	private String operacionARealizar;
	
	@JsonProperty("intentos_realizados")
	private String intentosRealizados;
	
	@JsonProperty("fecha_primer_intento")
	private String fechaPrimerIntento;
	
	@JsonProperty("fecha_ultimo_intento")
	private String fechaUltimoIntento;

	public DetailRequestFailInfo(String fechaInicio, String fechaFin, String proveedor, String producto, String cliente,
			String anexo, String operacionARealizar, String intentosRealizados,
			String fechaPrimerIntento, String fechaUltimoIntento) {
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.proveedor = proveedor;
		this.producto = producto;
		this.cliente = cliente;
		this.anexo = anexo;
		this.operacionARealizar = operacionARealizar;
		this.intentosRealizados = intentosRealizados;
		this.fechaPrimerIntento = fechaPrimerIntento;
		this.fechaUltimoIntento = fechaUltimoIntento;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public String getProveedor() {
		return proveedor;
	}

	public String getProducto() {
		return producto;
	}

	public String getCliente() {
		return cliente;
	}

	public String getAnexo() {
		return anexo;
	}

	public String getOperacionARealizar() {
		return operacionARealizar;
	}

	public String getIntentosRealizados() {
		return intentosRealizados;
	}

	public String getFechaPrimerIntento() {
		return fechaPrimerIntento;
	}

	public String getFechaUltimoIntento() {
		return fechaUltimoIntento;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public void setAnexo(String anexo) {
		this.anexo = anexo;
	}

	public void setOperacionARealizar(String operacionARealizar) {
		this.operacionARealizar = operacionARealizar;
	}

	public void setIntentosRealizados(String intentosRealizados) {
		this.intentosRealizados = intentosRealizados;
	}

	public void setFechaPrimerIntento(String fechaPrimerIntento) {
		this.fechaPrimerIntento = fechaPrimerIntento;
	}

	public void setFechaUltimoIntento(String fechaUltimoIntento) {
		this.fechaUltimoIntento = fechaUltimoIntento;
	}

	@Override
	public String toString() {
		return this.getProveedor() + "," +
				this.getProducto() + "," +
				this.getCliente() + "," +
				this.getAnexo() + "," +
				this.getOperacionARealizar() + "," +
				this.getIntentosRealizados() + "," +
				this.getFechaPrimerIntento() + "," +
				this.getFechaUltimoIntento();
	}
}
