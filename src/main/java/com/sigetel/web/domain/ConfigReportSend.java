package com.sigetel.web.domain;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "config_report_send")
public class ConfigReportSend implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "id_report", nullable = false)
	private Long idReport;

	@NotNull
	@Column(name = "time_schedule", nullable = true)
	private Instant time;

	@NotNull
	@Column(name = "format_type", nullable = false)
	private String formatType;

	@Column(name = "mail_copy")
	private String mailCopy;
	
	@Column(name = "last_send", nullable = true)
	private Instant lastSend;

	public Long getId() {
		return id;
	}

	public Long getIdReport() {
		return idReport;
	}

	public Instant getTime() {
		return time;
	}

	public String getFormatType() {
		return formatType;
	}

	public String getMailCopy() {
		return mailCopy;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIdReport(Long idReport) {
		this.idReport = idReport;
	}

	public void setTime(Instant time) {
		this.time = time;
	}

	public void setFormatType(String formatType) {
		this.formatType = formatType;
	}

	public void setMailCopy(String mailCopy) {
		this.mailCopy = mailCopy;
	}

	public Instant getLastSend() {
		return lastSend;
	}

	public void setLastSend(Instant lastSend) {
		this.lastSend = lastSend;
	}

	@Override
	public String toString() {
		return "ConfigReportSend{" 
				+ "id=" + getId() + ", id_report='" 
				+ getIdReport() + "'" 
				+ ", time='" + getTime()
				+ "'" + ", format_type='" 
				+ getFormatType() + "'" 
				+ ", mail_copy='" 
				+ getMailCopy() + "'" 
				+ ", last_send ='" 
				+ getLastSend() + "'" 
			+ "}";
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
		ConfigReportSend configReportSend = (ConfigReportSend) o;
		if (configReportSend.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), configReportSend.getId());
	}
}
