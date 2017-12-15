package com.sigetel.web.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

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
import com.sigetel.web.domain.AppConfig;
import com.sigetel.web.service.AppConfigService;
import com.sigetel.web.web.rest.util.HeaderUtil;

@RestController
@RequestMapping("/api")
public class AppConfigurationResource {


    private final Logger log = LoggerFactory.getLogger(AppConfigurationResource.class);

    private static final String ENTITY_NAME = "configurationResource";

    private final AppConfigService appConfigService;

    public AppConfigurationResource(AppConfigService appConfigService) {
        this.appConfigService = appConfigService;

    }

    @PostMapping("/appConfig")
    @Timed
    public ResponseEntity createAppConfig(@RequestBody AppConfig appConfigVM)
        throws URISyntaxException{

        AppConfig appConfig = appConfigService.createAppConfig(appConfigVM);

        return ResponseEntity.created(new URI("/api/appConfig/" + appConfig.getId()))
            .headers(HeaderUtil
                .createAlert("A new application configuration is created with identifier "
                    + appConfig.getId(), ""))
            .body(appConfig);
    }

    @PutMapping("/appConfig")
    @Timed
    public ResponseEntity<AppConfig> updateAppConfig(@Valid @RequestBody AppConfig appConfigVM){
        AppConfig existingConfig =
            appConfigService.findOneById(appConfigVM.getId());

        if (existingConfig.getId() != null &&
            (!existingConfig.getId()
                .equals(appConfigVM.getId()))) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "Config Existing", "Configuration already in use"))
                .body(null);
        }

        AppConfig updatedConfig = appConfigService
            .updateAppConfig(appConfigVM);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, appConfigVM.getId().toString()))
            .body(updatedConfig);
    }

    @GetMapping("/appConfig")
    @Timed
    public List<AppConfig> getAllAppConfig(){

        return appConfigService.findAllByOrderByIdAsc();
    }

    @DeleteMapping("/appConfig")
    @Timed
    public ResponseEntity<AppConfig> deleteAppConfig(@Valid @RequestBody Long id) {

        appConfigService.deleteAppConfig(id);

        return ResponseEntity.ok().headers(HeaderUtil.createAlert("An app configuration is deleted with identifier "
            + id, ""))
            .build();
    }

}
