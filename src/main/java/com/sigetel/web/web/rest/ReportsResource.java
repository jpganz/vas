package com.sigetel.web.web.rest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.sigetel.web.domain.BillingInfo;
import com.sigetel.web.domain.Conciliacion;
import com.sigetel.web.domain.DetailRequestFailInfo;
import com.sigetel.web.domain.Inconsistencias;
import com.sigetel.web.domain.PerformanceInfo;
import com.sigetel.web.domain.ProviderCommand;
import com.sigetel.web.domain.ProviderCommandRequest;
import com.sigetel.web.domain.Request;
import com.sigetel.web.service.ProviderCommandRequestService;
import com.sigetel.web.service.ProviderCommandService;
import com.sigetel.web.service.RequestService;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

@RestController
@RequestMapping("/api")
public class ReportsResource {

	private final Logger log = LoggerFactory.getLogger(ReportsResource.class);

	private static final String ENTITY_NAME = "provider";

	/*private final ProviderService providerService;

	private final RestClient restClient;

	private final JasperHelper jasperHelper;*/

	@Autowired
	JdbcTemplate jdbcTemplate;

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    public RequestService requestService;

    @Autowired
    private ProviderCommandRequestService providerCommandRequestService;

    @Autowired
    private ProviderCommandService providerCommandService;

	/*public ReportsResource(ProviderService providerService, RestClient restClient, JasperHelper jasperHelper) {
		this.providerService = providerService;
		this.restClient = restClient;
		this.jasperHelper = jasperHelper;
	}*/

	public ReportsResource() {
		/*this.providerService = null;
		this.restClient = null;
		this.jasperHelper = null;*/
	}

	@GetMapping("/reports/billing/{startDate}/{endDate}")
	public List<BillingInfo> billingReport(@PathVariable String startDate,@PathVariable String endDate ) {
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date startingDate;
        Date endingDate;
        try {
            startingDate = df.parse(startDate);
            endingDate = df.parse(endDate);
            return billingReportInformation(startingDate, endingDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
		return null;
	}

    @GetMapping("/reports/inconsistencias/{date}")
    public List<Inconsistencias> inconsistenciasReport(@PathVariable String date) {
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate;
        try {
            startDate = df.parse(date);
            return billingInconsistencies(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

    }

    @GetMapping("/reports/conciliacion/{date}")
    public List<Conciliacion> conciliacionReport(@PathVariable String date) {
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate;
        try {
            startDate = df.parse(date);
            return billingConciliacion(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/reports/billing")
	@Timed
	public void getBillingPdfReport() {
		String templateFileName = "billing.jrxml";
		String reportName = "billingReport";
		String reportFileName = "billing_report_";
		Date today = new Date();
		List<BillingInfo> allInfo = billingReportInformation(today, today);
		startGeneratingReport(templateFileName, reportName, reportFileName, allInfo);
    }

	@GetMapping("/reports/performance")
	@Timed
	public void generatePDFPerformanceReport() {
		String templateFileName = "performance.jrxml";
		String reportName = "Performance del orquestador";
		String reportFileName = "performance_report_";

		Date date = new Date();
		String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(date);

		List<PerformanceInfo> allInfo = getPerformanceReportData(yesterday().toString(), modifiedDate);

		startGeneratingReport(templateFileName, reportName, reportFileName, allInfo);
	}

	@GetMapping("/reports/performance/{fecha_inicio}/{fecha_fin}")
	public List<PerformanceInfo> performanceReport(@PathVariable String fecha_inicio, @PathVariable String fecha_fin) {
		return getPerformanceReportData(fecha_inicio, fecha_fin);
	}

	public void setJDBC(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<PerformanceInfo> getPerformanceReportData(String fecha_inicio, String fecha_fin) {
	    return null;
	    /* todo
		List<PerformanceInfo> info = new ArrayList<>();
		String queryReport = "SELECT r.id," +
				"       p.name proveedor," +
				"       pc.product producto," +
				"       sum(rt.try_number) promedio_intentos_operacion," +
				"       count(rt.request_id) operaciones_enviadas," +
				"       sum(CASE" +
				"               WHEN rt.status = 1 THEN 1" +
				"               ELSE 0" +
				"           END) operaciones_operadas_true," +
				"       sum(CASE" +
				"               WHEN rt.status = 0 THEN 1" +
				"               ELSE 0" +
				"           END) operaciones_operadas_false" +
				"  FROM request r" +
				"       JOIN request_try rt" +
				"            ON r.id = rt.request_id" +
				"            AND rt.date_time BETWEEN '" + fecha_inicio + "' AND '" + fecha_fin + "' " +
				"       JOIN provider_request pr" +
				"            ON r.id = pr.request_id" +
				"       JOIN provider_command pc" +
				"            ON pr.provider_command_id = pc.id" +
				"       JOIN provider p" +
				"            ON pc.provider_id = p.id" +
				"  group by r.id," +
				"       p.name," +
				"       pc.product";

		jdbcTemplate.query(queryReport,
            new RowMapper<Object>() {
                @Override
                public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return new PerformanceInfo(fecha_inicio, fecha_fin, rs.getString("proveedor"), rs.getString("producto"),
                        rs.getString("operaciones_enviadas"), rs.getString("operaciones_operadas_true"),
                        rs.getString("operaciones_operadas_false"), rs.getString("promedio_intentos_operacion"));
                }
            })
				.forEach(performanceInfo -> {
                    return info.add(performanceInfo);
                });

		return info;*/
	}

	@GetMapping("/reports/requestFail")
	@Timed
	public void generatePDFRequestFailReport() {
		String templateFileName = "requestFail.jrxml";
		String reportName = "Detalle de operaciones no procesadas";
		String reportFileName = "requestFail_report_";

		Date date = new Date();
		String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(date);

		List<DetailRequestFailInfo> allInfo = getDetailRequestFailReportData(yesterday().toString(), modifiedDate);

		startGeneratingReport(templateFileName, reportName, reportFileName, allInfo);
	}

	@GetMapping("/reports/requestFail/{fecha_inicio}/{fecha_fin}")
	public List<DetailRequestFailInfo> requestFailReport(@PathVariable String fecha_inicio, @PathVariable String fecha_fin) {
		return getDetailRequestFailReportData(fecha_inicio, fecha_fin);
	}

	public List<DetailRequestFailInfo> getDetailRequestFailReportData(String fecha_inicio, String fecha_fin){
	    return null;
		/* todo
		List<DetailRequestFailInfo> info = new ArrayList<>();
		String queryReport = "SELECT r.id," +
				"       p.name proveedor," +
				"       pc.product producto," +
				"       r.client_name cliente," +
				"       r.annexed_number anexo," +
				"       c.name operacion_a_realizar," +
				"       sum(rt.try_number) intentos_realizados," +
				"       t.fecha_primer_intento," +
				"       t.fecha_ultimo_intento" +
				"  FROM request r" +
				"       JOIN request_try rt" +
				"            ON r.id = rt.request_id" +
				"               AND rt.date_time BETWEEN '" + fecha_inicio + "' AND '" + fecha_fin + "' " +
				"       JOIN provider_request pr" +
				"            ON r.id = pr.request_id" +
				"       JOIN provider_command pc" +
				"            ON pr.provider_command_id = pc.id" +
				"       JOIN provider p" +
				"            ON pc.provider_id = p.id" +
				"       JOIN command c" +
				"            ON pc.command_id = c.id" +
				"       JOIN (SELECT r.id," +
				"                    min(rt.date_time) fecha_primer_intento," +
				"                    max(rt.date_time) fecha_ultimo_intento" +
				"               FROM request r" +
				"                    JOIN request_try rt" +
				"                         ON r.id = rt.request_id" +
				"              GROUP BY r.id) T" +
				"           ON r.id = T.id" +
				" GROUP BY r.id," +
				"          p.name," +
				"          pc.product," +
				"          r.client_name," +
				"          r.annexed_number," +
				"          c.name," +
				"          t.fecha_primer_intento," +
				"          t.fecha_ultimo_intento";

		jdbcTemplate.query(queryReport,
            new RowMapper<Object>() {
                @Override
                public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return new DetailRequestFailInfo(
                        fecha_inicio, fecha_fin,
                        rs.getString("proveedor"), rs.getString("producto"),
                        rs.getString("cliente"), rs.getString("anexo"),
                        rs.getString("operacion_a_realizar"), rs.getString("intentos_realizados"),
                        rs.getString("fecha_primer_intento"), rs.getString("fecha_ultimo_intento"));
                }
            })
				.forEach(detailRequestFailInfo -> info.add(detailRequestFailInfo));

		return info;*/
	}

	private <T> void startGeneratingReport(String templateFileName, String reportName, String reportFileName,
			List<T> allInfo) {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(templateFileName).getFile());
		file.getPath();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		FileOutputStream fos = null;
		try {
			byte[] bytes = generateJasperReportPDF(reportName, outputStream, allInfo, templateFileName);
			if (bytes.length > 1) {

				String path = System.getProperty("user.home");
				path += File.separator + "reports";
				File customDir = new File(path);

				if (customDir.exists()) {
				    System.out.println(customDir + " already exists");
				} else if (customDir.mkdirs()) {
				    System.out.println(customDir + " was created");
				} else {
				    System.out.println(customDir + " was not created");
				}


				File someFile = new File(customDir + File.separator + reportFileName + yesterday().toString() + ".pdf");

				fos = new FileOutputStream(someFile);
				fos.write(bytes);
				fos.flush();
				fos.close();
				System.out.println("<<<<<<<<<<<<Report Created>>>>>>>>");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public byte[] generateJasperReportPDF(String jasperReportName, ByteArrayOutputStream outputStream, List reportList,
			String fileName) {
		JRPdfExporter exporter = new JRPdfExporter();
		try {
			String reportLocation = getFileWithUtil(fileName);

			JasperReport jasperReport = JasperCompileManager.compileReport(reportLocation);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null,
					new JRBeanCollectionDataSource(reportList));
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
			exporter.exportReport();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error in generate Report..." + e);
		} finally {
		}
		return outputStream.toByteArray();
	}

	private String getFileWithUtil(String fileName) throws URISyntaxException {
		return Paths.get(getClass().getClassLoader().getResource(fileName).toURI()).toString();
	}

	private String yesterday() {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(cal.getTime());
	}

	@Transactional
	private List<BillingInfo> billingReportInformation(Date initDate, Date endDate) {
	    return null;
	    /* todo
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate initLocalDate = initDate.toInstant().atZone(defaultZoneId).toLocalDate();

        LocalDateTime startMidnight = LocalDateTime.of(initLocalDate, midnight);

        LocalDate endLocalDate = endDate.toInstant().atZone(defaultZoneId).toLocalDate();
        LocalDateTime endMidnight = LocalDateTime.of(endLocalDate, midnight);

	    List<Request> requests = requestService.findByDatetimeBetween(startMidnight.toLocalDate(), endMidnight.toLocalDate());
		List<BillingInfo> allInfo = new ArrayList<>();
		System.out.println("billingReportInformation: " + requests.size());
		for (Request request:requests) {
		    String status = StringUtils.EMPTY;
            String tryDate = StringUtils.EMPTY;
            request.getRequestTries();
            if(request.getRequestTries().size() > 0){
		        status = request.getRequestTries().get(0).getStatus().toString();
                tryDate = request.getRequestTries().get(0).getDateTime().toString();
            }
			BillingInfo billingInfo = new BillingInfo();
			billingInfo.setCodigoServicio(request.getServiceCode());
			billingInfo.setConfirmacionEvent(request.getEventConfirmation());
			billingInfo.setEstado(status); // requestTry.status need to figure out best way to avoid this =)
			billingInfo.setConfirmacionEvent(request.getEventConfirmation());
			billingInfo.setFechaEstado(tryDate); // AGREGAR FECHA A REQUEST TRY
//			billingInfo.setMontoPlan(request.getRequestAmount().toString());
			billingInfo.setNombreCliente(request.getClientName());
			billingInfo.setNumeroAnexo(request.getAnnexedNumber().toString());
			billingInfo.setNumeroCliente(request.getClientName());
			billingInfo.setNumeroTelefono("");
			allInfo.add(billingInfo);
		}
		return allInfo;*/
	}

    @Transactional
    private List<Inconsistencias> billingInconsistencies(Date date) {
	    return null;
	    /* todo
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate today = LocalDate.now();
        LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
        LocalDateTime yesterdayMidnight = todayMidnight.minusDays(1);
        List<Request> requests = requestService.findByDatetimeBetween(yesterdayMidnight.toLocalDate(), todayMidnight.toLocalDate());
        List<Inconsistencias> inconsistencias = new ArrayList<Inconsistencias>();
        for(Request req:requests){
            Inconsistencias inco = new Inconsistencias();
            inco.setAnexco(req.getAnnexedNumber().toString());
            inco.setCliente(req.getClientName());
            inco.setFechaFin(todayMidnight.toLocalDate().toString());
            inco.setFechaInicio(yesterdayMidnight.toLocalDate().toString());
            inco.setFechaOperacionAs400(req.getDateTime().toString());
            String tryDate = StringUtils.EMPTY;
            req.getRequestTries();
            if(req.getRequestTries().size() > 0){
                tryDate = req.getRequestTries().get(0).getDateTime().toString();
            }
            //todo: fix this!!
            ProviderCommandRequest provRequest = providerCommandRequestService.findByRequestId(req.getId());
            // FIX THIS AWFULLY SAD SHIT NO RELATION
            if(provRequest != null){
                ProviderCommand command = providerCommandService.findOne(provRequest.getProviderCommandId());
                inco.setProducto(command.getProducto());
                inco.setProveedor(command.getCommand().getName());
            }
            inco.setFechaOperacionVas(tryDate);
            inco.setStatusAs400(req.getStatusRequested());
            inco.setStatusVas(req.getRequestStatus().toString());
            inconsistencias.add(inco);
        }
        return inconsistencias; */
    }

    @Transactional
    private List<Conciliacion> billingConciliacion(Date date) {
	    return null;
	    /* todo
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDate today = LocalDate.now();
        LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);
        LocalDateTime yesterdayMidnight = todayMidnight.minusDays(1);
        List<Request> requests = requestService.findByDatetimeBetween(yesterdayMidnight.toLocalDate(), todayMidnight.toLocalDate());
        List<Conciliacion> inconsistencias = new ArrayList<Conciliacion>();
        for(Request req:requests){
            Conciliacion conciliacion = new Conciliacion();
            conciliacion.setFechaFin(todayMidnight.toLocalDate().toString());
            conciliacion.setFechaInicio(yesterdayMidnight.toLocalDate().toString());
            String tryDate = StringUtils.EMPTY;
            req.getRequestTries();
            if(req.getRequestTries().size() > 0){
                tryDate = req.getRequestTries().get(0).getDateTime().toString();
            }
            //todo: fix this!!
            ProviderCommandRequest provRequest = providerCommandRequestService.findByRequestId(req.getId());
            // FIX THIS AWFULLY SAD SHIT NO RELATION
            if(provRequest != null){
                ProviderCommand command = providerCommandService.findOne(provRequest.getProviderCommandId());
                conciliacion.setProducto(command.getProducto());
                conciliacion.setProveedor(command.getCommand().getName());
            }

            String altasAs400 = "N/A";
            String bajasAs400 = "N/A";
            String suspencionAs400 = "N/A";
            if(req != null){
                if (req.getStatusRequested().equals("ALTA")) {
                    altasAs400 = "X";
                }
                if (req.getStatusRequested().equals("BAJA")) {
                    bajasAs400 = "X";
                }
                if (req.getStatusRequested().equals("SUSPENCION")) {
                    suspencionAs400 = "X";
                }
            }
            conciliacion.setAltasAs400(altasAs400); // query to get all
            conciliacion.setAltasOvas("N/A");
            conciliacion.setBajasAs400(bajasAs400);
            conciliacion.setBajasOvas("N/A");
            conciliacion.setSuspencionAs400(suspencionAs400);
            conciliacion.setSuspencionOvas("N/A");

            inconsistencias.add(conciliacion);
        }
        return inconsistencias;*/
    }
}
