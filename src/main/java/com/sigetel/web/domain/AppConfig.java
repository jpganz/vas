package com.sigetel.web.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "app_configuration")
public class AppConfig implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "name", nullable = false)
	private String name;

	@NotNull
	@Column(name = "route", nullable = false)
	private String route;

	@NotNull
	@Column(name = "value", nullable = false)
	private String value;

	@Column(name = "report")
	private Long report;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getRoute() {
		return route;
	}

	public String getValue() {
		return value;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getReport() {
		return report;
	}

	public void setReport(Long report) {
		this.report = report;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		AppConfig appConfig = (AppConfig) o;
		if (appConfig.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), appConfig.getId());
	}

	   @Override
	    public String toString() {
	        return "AppConfig{" +
	            "id=" + getId() +
	            ", name='" + getName() + "'" +
	            ", route='" + getRoute() + "'" +
	            ", value='" + getValue() + "'" +
	            ", report='" + getReport() + "'" +
	            "}";
	    }
}
