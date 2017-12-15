package com.sigetel.web.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PerformanceInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("fecha_inicio")
	private String fechaInicio;
	
	@JsonProperty("fecha_fin")
	private String fechaFin;
	
	@JsonProperty("proveedor")
	private String proveedor;
	
	@JsonProperty("producto")
	private String producto;
	
	@JsonProperty("operaciones_enviadas")
	private String opereacionesEnviadas;
	
	@JsonProperty("operaciones_operadas_true")
	private String operacionesOperadasTrue;
	
	@JsonProperty("operaciones_operadas_false")
	private String operacionesOperadasFalse;
	
	@JsonProperty("promedio_intentos_operacion")
	private String promedioIntentosOperacion;
	
	public PerformanceInfo(String proveedor, String producto, String opereacionesEnviadas,
			String operacionesOperadasTrue, String operacionesOperadasFalse, String promedioIntentosOperacion) {
		this.proveedor = proveedor;
		this.producto = producto;
		this.opereacionesEnviadas = opereacionesEnviadas;
		this.operacionesOperadasTrue = operacionesOperadasTrue;
		this.operacionesOperadasFalse = operacionesOperadasFalse;
		this.promedioIntentosOperacion = promedioIntentosOperacion;
	}
	
	

	public PerformanceInfo(String fechaInicio, String fechaFin, String proveedor, String producto,
			String opereacionesEnviadas, String operacionesOperadasTrue, String operacionesOperadasFalse,
			String promedioIntentosOperacion) {
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.proveedor = proveedor;
		this.producto = producto;
		this.opereacionesEnviadas = opereacionesEnviadas;
		this.operacionesOperadasTrue = operacionesOperadasTrue;
		this.operacionesOperadasFalse = operacionesOperadasFalse;
		this.promedioIntentosOperacion = promedioIntentosOperacion;
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

	public String getOperacionesEnviadas() {
		return opereacionesEnviadas;
	}

	public String getOperacionesOperadasTrue() {
		return operacionesOperadasTrue;
	}

	public String getOperacionesOperadasFalse() {
		return operacionesOperadasFalse;
	}

	public String getPromedioIntentosOperacion() {
		return promedioIntentosOperacion;
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

	public void setOperacionesEnviadas(String opereacionesEnviadas) {
		this.opereacionesEnviadas = opereacionesEnviadas;
	}

	public void setOperacionesOperadasTrue(String operacionesOperadasTrue) {
		this.operacionesOperadasTrue = operacionesOperadasTrue;
	}

	public void setOperacionesOperadasFalse(String operacionesOperadasFalse) {
		this.operacionesOperadasFalse = operacionesOperadasFalse;
	}

	public void setPromedioIntentosOperacion(String promedioIntentosOperacion) {
		this.promedioIntentosOperacion = promedioIntentosOperacion;
	}
	@Override
	public String toString() {
		return this.getProveedor() + "," +
				this.getProducto() + "," +
				this.getOperacionesEnviadas() + "," +
				this.getPromedioIntentosOperacion() + "," +
				this.getOperacionesOperadasTrue() + "," +
				this.getOperacionesOperadasFalse();
	}
	
	
}
