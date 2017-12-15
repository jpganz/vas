package com.sigetel.web.scheduler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.opencsv.CSVWriter;
import com.sigetel.web.domain.ConfigReportSend;
import com.sigetel.web.mail.MailBody;
import com.sigetel.web.mail.MailMail;
import com.sigetel.web.repository.ConfigReportSendRepository;
import com.sigetel.web.service.AppConfigService;
import com.sigetel.web.service.RequestService;
import com.sigetel.web.service.RequestTryService;
import com.sigetel.web.web.rest.ReportsResource;

@Service
@EnableScheduling
public class SchedulerReportSenderTask {

	private final ConfigReportSendRepository configReportSendRepository;

	@Autowired
	private AppConfigService appConfigService;
	private Long reportId = 0L;

	@Autowired
	RequestTryService requestTryService;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	private RequestService requestService;

	public SchedulerReportSenderTask(ConfigReportSendRepository configReportSendRepository, 
			AppConfigService appConfigService) {
		this.configReportSendRepository = configReportSendRepository;
		this.appConfigService = appConfigService;
	}

	// 0 0 * ? * *
	@Scheduled(cron = "10 * * * * *")
	public void callReportSender() {
		System.out.println(new Date() + " Go TO review tasks and send reports");

		checkDatabaseForReportTime();
	}

	private String yesterday() {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(cal.getTime());
	}

	public void checkDatabaseForReportTime() {
		Instant dateNow = Instant.now();

		List<ConfigReportSend> listConfirReportSend = configReportSendRepository.findAll();

		for (ConfigReportSend configReportSend : listConfirReportSend) {

			LocalDateTime ldtLastSend;

			if (null == configReportSend.getLastSend()) {
				ldtLastSend = LocalDateTime.ofInstant(configReportSend.getTime(), ZoneId.systemDefault());
			} else {
				ldtLastSend = LocalDateTime.ofInstant(configReportSend.getLastSend(), ZoneId.systemDefault());
			}

			LocalDateTime ldt = LocalDateTime.ofInstant(dateNow, ZoneId.systemDefault());

			int elapsed = Math.abs(ldtLastSend.getHour() - ldt.getHour());

			if (elapsed == 0) {
				getReportAndConvertToCVS(configReportSend);
			} else {
				System.out.println("No time report to send. " + ldtLastSend.toString() + " - " + ldt.toString());
			}

		}
	}

	public void getReportAndConvertToCVS(ConfigReportSend configReportSend) {

		Date date = new Date();
		String modifiedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
		boolean reportHasData = false;
		String reportFileName = "";

		reportId = configReportSend.getIdReport();

		String configReportName = appConfigService.findOneByRoute("report.file").getValue();

		ReportsResource reportsResource = new ReportsResource();

		switch (configReportSend.getIdReport().toString()) {
		case "1":// Billing
			reportFileName = "billing_cvs_" + modifiedDate;
			reportFileName = configReportName + File.separator + reportFileName + ".csv";
			String[] headerReportBilling = { "Monto plan", "# de Cliente", "# Anexo", "Nombre del Cliente",
					"# Teléfono", "Estado", "Fecha estado actual", "Código Servicio", "Confirmación del Evento" };
			reportsResource.requestService = this.requestService;
			reportHasData = createCVSFile(reportsResource.billingReport(modifiedDate,modifiedDate  ), reportFileName,
					headerReportBilling);

			break;
		case "2":// Performance
			reportFileName = "performance_cvs_" + modifiedDate;
			reportFileName = configReportName + File.separator + reportFileName + ".csv";
			String[] headerReportPerformance = { "proveedor", "producto", "# Operaciones Enviadas",
					"Promedio de intentos por operación", "# Operaciones Operadas Exitosamente",
					"# Operaciones Fallidas (permanentemente)" };
			reportsResource.setJDBC(jdbcTemplate);
			reportHasData = createCVSFile(
					reportsResource.getPerformanceReportData(yesterday().toString(), modifiedDate), reportFileName,
					headerReportPerformance);

			break;
		case "3":// Conciliacion
			reportFileName = "conciliacion_cvs_" + modifiedDate;
			reportFileName = configReportName + File.separator + reportFileName + ".csv";
			String[] headerReportConciliacion = { "Proveedor (VAS)", "Producto (VAS)", "Altas Operadas AS/400",
					"Altas Operadas Orquestador VAS", "Bajas Operadas AS/400", "Bajas Operadas Orquestador VAS",
					"Suspensiones Operadas AS/400", "Suspensiones Operadas Orquestador VAS" };
			reportsResource.requestService = this.requestService;
			reportHasData = createCVSFile(reportsResource.conciliacionReport(modifiedDate), reportFileName,
					headerReportConciliacion);

			break;
		case "4":// Inconsistencias
			reportFileName = "inconsistencias_cvs_" + modifiedDate;
			reportFileName = configReportName + File.separator + reportFileName + ".csv";
			String[] headerReportInconsistencias = { "Proveedor (VAS)", "Producto (VAS)", "Cliente", "Anexo",
					"Estatus AS/400", "Fecha de Operación AS/400", "Estatus Orquestador VAS",
					"Fecha de Operación VAS" };
			reportsResource.requestService = this.requestService;
			reportHasData = createCVSFile(reportsResource.inconsistenciasReport(modifiedDate), reportFileName,
					headerReportInconsistencias);

			break;
		case "5":// Detail Request Fail
			reportFileName = "requestFail_cvs_" + modifiedDate;
			reportFileName = configReportName + File.separator + reportFileName + ".csv";
			String[] headerReportRequestFail = { "Proveedor (VAS)", "Producto (VAS)", "Cliente", "Anexo",
					"Operación a realizar", "Descripción del Error", "# Intentos realizados", "Fecha primer intento",
					"Fecha último intento" };
			reportsResource.setJDBC(jdbcTemplate);
			reportHasData = createCVSFile(
					reportsResource.getDetailRequestFailReportData(yesterday().toString(), modifiedDate),
					reportFileName, headerReportRequestFail);

			break;
		}

		if (reportHasData) {
			sendReportByConfiguration(configReportSend, reportFileName);
		} else {
			System.out.println("Report has no data to send.");
		}

	}

	private <T> boolean createCVSFile(List<T> reportData, String reportFileName, String[] headerReport) {

		if (reportData.size() > 0) {

			CSVWriter writer;
			try {
				writer = new CSVWriter(new FileWriter(reportFileName), ';', CSVWriter.DEFAULT_QUOTE_CHARACTER,
						CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
				writer.writeNext(headerReport);

				Iterator<T> iterator = reportData.iterator();

				while (iterator.hasNext()) {
					T currentValue = iterator.next();

					writer.writeNext(currentValue.toString().split(","));
				}

				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}

		return false;

	}

	private void sendReportByConfiguration(ConfigReportSend configReportSend, String CVSExportFile) {

		if (configReportSend.getFormatType().equals("M")) {

			List<String> listMails = new ArrayList<String>();

			if (configReportSend.getMailCopy().length() > 0) {
				String[] mailsCopy = configReportSend.getMailCopy().split(";");
				for (String mail : mailsCopy) {
					listMails.add(mail);
				}
			}

			if (listMails.size() > 0) {
				sendMailReportFile(listMails, CVSExportFile);
			}
		}

		if (configReportSend.getFormatType().equals("F")) {
			System.out.println("ENTERS FTP");
			sendFileToFTP(CVSExportFile);
		}

		updateLastSended(configReportSend);
	}

	private void sendMailReportFile(List<String> listMails, String filePath) {
		System.out.println("SEND MAIL");

		MailMail mail = new MailMail();

		mail.setMailSender(mail.getDefaultMailConfiguration(appConfigService));

		MailBody mailBody = new MailBody();
		mailBody.setFrom(appConfigService.findOneByRoute("mail.username").getValue());
		mailBody.setMessage("Reporte de OVAS.");
		mailBody.setSubject("Reporte Ovas");

		String[] mailArray = new String[listMails.size()];
		mailArray = listMails.toArray(mailArray);

		mailBody.setToArray(mailArray);
		mailBody.setAttachment(filePath);

		mail.constructMail(mailBody);
	}

	private void sendFileToFTP(String filePath) {

		FTPClient ftpClient = null;

		String server = appConfigService.findOneByRouteAndReport("ftp.server", reportId).getValue();
		String port = appConfigService.findOneByRouteAndReport("ftp.port", reportId).getValue();
		String user = appConfigService.findOneByRouteAndReport("ftp.user", reportId).getValue();
		String pass = appConfigService.findOneByRouteAndReport("ftp.password", reportId).getValue();
		String security = appConfigService.findOneByRouteAndReport("ftp.security", reportId).getValue();
		String ftpDirectory = appConfigService.findOneByRouteAndReport("ftp.directory", reportId).getValue();

		System.out.println("SERVER: " + server + "\n" + "PORT: " + port + "\n" + "USER: " + user + "\n" + "PASS: "
				+ pass + "\n" + "FILE: " + filePath + "\n");

		if (security == null || security.isEmpty()) {
			ftpClient = new FTPClient();
		} else {
			ftpClient = (FTPSClient) ftpClient;
			ftpClient = new FTPSClient();
		}

		try {
			ftpClient.connect(server, Integer.parseInt(port));
			ftpClient.login(user, pass);
			ftpClient.enterLocalPassiveMode();

			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			File firstLocalFile = new File(filePath);
			if (firstLocalFile.exists()) {
				InputStream inputStream = new FileInputStream(firstLocalFile);
				System.out.println("Start uploading first file");
				boolean done = ftpClient.storeFile(ftpDirectory + firstLocalFile.getName(), inputStream);
				inputStream.close();

				if (done) {
					System.out.println("The first file is uploaded successfully.");
				} else {
					System.out.println("The file was not uploaded. " + ftpClient.getStatus() + " - "
							+ ftpClient.getStatus(filePath));
				}
			} else {
				System.out.println("File doesn't exists. Verify it: " + filePath);
			}
		} catch (IOException ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

	private void updateLastSended(ConfigReportSend configReportSend) {

		configReportSendRepository.updateLastSend(Instant.now(), configReportSend.getId());
	}
}
