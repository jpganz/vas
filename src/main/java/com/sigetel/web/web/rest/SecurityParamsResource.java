package com.sigetel.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sigetel.web.domain.SecurityParams;
import com.sigetel.web.service.SecurityParamsService;
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
 * REST controller for managing SecurityParams.
 */
@RestController
@RequestMapping("/api")
public class SecurityParamsResource {

    private final Logger log = LoggerFactory.getLogger(SecurityParamsResource.class);

    private static final String ENTITY_NAME = "securityParams";

    private final SecurityParamsService securityParamsService;

    public SecurityParamsResource(SecurityParamsService securityParamsService) {
        this.securityParamsService = securityParamsService;
    }

    /**
     * POST  /security-params : Create a new securityParams.
     *
     * @param securityParams the securityParams to create
     * @return the ResponseEntity with status 201 (Created) and with body the new securityParams, or with status 400 (Bad Request) if the securityParams has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/security-params")
    @Timed
    public ResponseEntity<SecurityParams> createSecurityParams(@Valid @RequestBody SecurityParams securityParams) throws URISyntaxException {
        log.debug("REST request to save SecurityParams : {}", securityParams);
        if (securityParams.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new securityParams cannot already have an ID")).body(null);
        }
        SecurityParams result = securityParamsService.save(securityParams);
        return ResponseEntity.created(new URI("/api/security-params/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /security-params : Updates an existing securityParams.
     *
     * @param securityParams the securityParams to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated securityParams,
     * or with status 400 (Bad Request) if the securityParams is not valid,
     * or with status 500 (Internal Server Error) if the securityParams couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/security-params")
    @Timed
    public ResponseEntity<SecurityParams> updateSecurityParams(@Valid @RequestBody SecurityParams securityParams) throws URISyntaxException {
        log.debug("REST request to update SecurityParams : {}", securityParams);
        if (securityParams.getId() == null) {
            return createSecurityParams(securityParams);
        }
        SecurityParams result = securityParamsService.save(securityParams);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, securityParams.getId().toString()))
            .body(result);
    }

    /**
     * GET  /security-params : get all the securityParams.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of securityParams in body
     */
    @GetMapping("/security-params")
    @Timed
    public List<SecurityParams> getAllSecurityParams() {
        log.debug("REST request to get all SecurityParams");
        return securityParamsService.findAll();
    }

    /**
     * GET  /security-params/:id : get the "id" securityParams.
     *
     * @param id the id of the securityParams to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the securityParams, or with status 404 (Not Found)
     */
    @GetMapping("/security-params/{id}")
    @Timed
    public ResponseEntity<SecurityParams> getSecurityParams(@PathVariable Long id) {
        log.debug("REST request to get SecurityParams : {}", id);
        SecurityParams securityParams = securityParamsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(securityParams));
    }

    /**
     * DELETE  /security-params/:id : delete the "id" securityParams.
     *
     * @param id the id of the securityParams to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/security-params/{id}")
    @Timed
    public ResponseEntity<Void> deleteSecurityParams(@PathVariable Long id) {
        log.debug("REST request to delete SecurityParams : {}", id);
        securityParamsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
