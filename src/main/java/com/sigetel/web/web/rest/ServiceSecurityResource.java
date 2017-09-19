package com.sigetel.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sigetel.web.domain.ServiceSecurity;
import com.sigetel.web.service.ServiceSecurityService;
import com.sigetel.web.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ServiceSecurity.
 */
@RestController
@RequestMapping("/api")
public class ServiceSecurityResource {

    private final Logger log = LoggerFactory.getLogger(ServiceSecurityResource.class);

    private static final String ENTITY_NAME = "serviceSecurity";

    private final ServiceSecurityService serviceSecurityService;

    public ServiceSecurityResource(ServiceSecurityService serviceSecurityService) {
        this.serviceSecurityService = serviceSecurityService;
    }

    /**
     * POST  /service-securities : Create a new serviceSecurity.
     *
     * @param serviceSecurity the serviceSecurity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new serviceSecurity, or with status 400 (Bad Request) if the serviceSecurity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/service-securities")
    @Timed
    public ResponseEntity<ServiceSecurity> createServiceSecurity(@Valid @RequestBody ServiceSecurity serviceSecurity) throws URISyntaxException {
        log.debug("REST request to save ServiceSecurity : {}", serviceSecurity);
        if (serviceSecurity.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new serviceSecurity cannot already have an ID")).body(null);
        }
        ServiceSecurity result = serviceSecurityService.save(serviceSecurity);
        return ResponseEntity.created(new URI("/api/service-securities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /service-securities : Updates an existing serviceSecurity.
     *
     * @param serviceSecurity the serviceSecurity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated serviceSecurity,
     * or with status 400 (Bad Request) if the serviceSecurity is not valid,
     * or with status 500 (Internal Server Error) if the serviceSecurity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/service-securities")
    @Timed
    public ResponseEntity<ServiceSecurity> updateServiceSecurity(@Valid @RequestBody ServiceSecurity serviceSecurity) throws URISyntaxException {
        log.debug("REST request to update ServiceSecurity : {}", serviceSecurity);
        if (serviceSecurity.getId() == null) {
            return createServiceSecurity(serviceSecurity);
        }
        ServiceSecurity result = serviceSecurityService.save(serviceSecurity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, serviceSecurity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /service-securities : get all the serviceSecurities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of serviceSecurities in body
     */
    @GetMapping("/service-securities")
    @Timed
    public List<ServiceSecurity> getAllServiceSecurities() {
        log.debug("REST request to get all ServiceSecurities");
        return serviceSecurityService.findAll();
    }

    /**
     * GET  /service-securities/:id : get the "id" serviceSecurity.
     *
     * @param id the id of the serviceSecurity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the serviceSecurity, or with status 404 (Not Found)
     */
    @GetMapping("/service-securities/{id}")
    @Timed
    public ResponseEntity<ServiceSecurity> getServiceSecurity(@PathVariable Long id) {
        log.debug("REST request to get ServiceSecurity : {}", id);
        ServiceSecurity serviceSecurity = serviceSecurityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(serviceSecurity));
    }

    /**
     * DELETE  /service-securities/:id : delete the "id" serviceSecurity.
     *
     * @param id the id of the serviceSecurity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/service-securities/{id}")
    @Timed
    public ResponseEntity<Void> deleteServiceSecurity(@PathVariable Long id) {
        log.debug("REST request to delete ServiceSecurity : {}", id);
        serviceSecurityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
