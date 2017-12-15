package com.sigetel.web.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.sigetel.web.domain.ConfigReportSend;
import com.sigetel.web.service.ConfigReportSendService;
import com.sigetel.web.web.rest.util.HeaderUtil;

@RestController
@RequestMapping("/api")
public class ConfigReportSendResource {

	private final Logger log = LoggerFactory.getLogger(ConfigReportSendResource.class);

	private static final String ENTITY_NAME = "configReportsSend";

	private final ConfigReportSendService configReportSendService;

	public ConfigReportSendResource(ConfigReportSendService configReportSendService) {
		this.configReportSendService = configReportSendService;
	}

	@PostMapping("/configReportSend")
	@Timed
	public ResponseEntity createConfigReportSend(@RequestBody ConfigReportSend configReportSendVM)
			throws URISyntaxException {
		log.debug("REST request to create Configuration : {}", configReportSendVM.toString());
		
		ConfigReportSend configReportSend = configReportSendService
				.createConfigReportSend(configReportSendVM.getId(),
						configReportSendVM.getIdReport(),
						configReportSendVM.getTime(),
						configReportSendVM.getFormatType(),
						configReportSendVM.getMailCopy());

		return ResponseEntity.created(new URI("/api/configReportSend/" + configReportSend.getId()))
				.headers(HeaderUtil
						.createAlert("A new configuration is created with identifier " + configReportSend.getId(), ""))
				.body(configReportSend);
	}

	@PutMapping("/configReportSend")
	@Timed
	public ResponseEntity<ConfigReportSend> updateConfigReportSend(
			@RequestBody ConfigReportSend configReportSendVM) {
		log.debug("REST request to update Configuration : {}", configReportSendVM);

		ConfigReportSend existingConfig = configReportSendService.findOneById(configReportSendVM.getId());

		if (existingConfig.getId() != null && 
				(!existingConfig.getId()
						.equals(configReportSendVM.getId()))) {
			return ResponseEntity.badRequest()
					.headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "Config Existing", "Configuration already in use"))
					.body(null);
		}

		ConfigReportSend updatedConfig = configReportSendService
				.updateConfigReportSend(configReportSendVM);
		
		return ResponseEntity.ok()
	            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, configReportSendVM.getId().toString()))
	            .body(updatedConfig);
	}
	
	@GetMapping("/configReportSend")
    @Timed
    public List<ConfigReportSend> getAllAppConfig(){

        return configReportSendService.findAllByOrderByIdAsc();
    }

	@DeleteMapping("/configReportSend")
	@Timed
	public ResponseEntity<ConfigReportSend> deleteConfigReportSend(@RequestBody Long id) {
		log.debug("REST request to delete Configuration : {}", id);

		configReportSendService.deleteConfigReportSend(id);

		return ResponseEntity.ok().headers(HeaderUtil.createAlert("A configuration is deleted with identifier " 
				+ id, ""))
				.build();
	}
}
