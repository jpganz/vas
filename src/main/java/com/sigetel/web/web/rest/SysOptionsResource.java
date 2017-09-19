package com.sigetel.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sigetel.web.domain.SysOptions;
import com.sigetel.web.service.SysOptionsService;
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
 * REST controller for managing SysOptions.
 */
@RestController
@RequestMapping("/api")
public class SysOptionsResource {

    private final Logger log = LoggerFactory.getLogger(SysOptionsResource.class);

    private static final String ENTITY_NAME = "sysOptions";

    private final SysOptionsService sysOptionsService;

    public SysOptionsResource(SysOptionsService sysOptionsService) {
        this.sysOptionsService = sysOptionsService;
    }

    /**
     * POST  /sys-options : Create a new sysOptions.
     *
     * @param sysOptions the sysOptions to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sysOptions, or with status 400 (Bad Request) if the sysOptions has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sys-options")
    @Timed
    public ResponseEntity<SysOptions> createSysOptions(@Valid @RequestBody SysOptions sysOptions) throws URISyntaxException {
        log.debug("REST request to save SysOptions : {}", sysOptions);
        if (sysOptions.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new sysOptions cannot already have an ID")).body(null);
        }
        SysOptions result = sysOptionsService.save(sysOptions);
        return ResponseEntity.created(new URI("/api/sys-options/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sys-options : Updates an existing sysOptions.
     *
     * @param sysOptions the sysOptions to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sysOptions,
     * or with status 400 (Bad Request) if the sysOptions is not valid,
     * or with status 500 (Internal Server Error) if the sysOptions couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sys-options")
    @Timed
    public ResponseEntity<SysOptions> updateSysOptions(@Valid @RequestBody SysOptions sysOptions) throws URISyntaxException {
        log.debug("REST request to update SysOptions : {}", sysOptions);
        if (sysOptions.getId() == null) {
            return createSysOptions(sysOptions);
        }
        SysOptions result = sysOptionsService.save(sysOptions);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sysOptions.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sys-options : get all the sysOptions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sysOptions in body
     */
    @GetMapping("/sys-options")
    @Timed
    public List<SysOptions> getAllSysOptions() {
        log.debug("REST request to get all SysOptions");
        return sysOptionsService.findAll();
    }

    /**
     * GET  /sys-options/:id : get the "id" sysOptions.
     *
     * @param id the id of the sysOptions to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sysOptions, or with status 404 (Not Found)
     */
    @GetMapping("/sys-options/{id}")
    @Timed
    public ResponseEntity<SysOptions> getSysOptions(@PathVariable Long id) {
        log.debug("REST request to get SysOptions : {}", id);
        SysOptions sysOptions = sysOptionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sysOptions));
    }

    /**
     * DELETE  /sys-options/:id : delete the "id" sysOptions.
     *
     * @param id the id of the sysOptions to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sys-options/{id}")
    @Timed
    public ResponseEntity<Void> deleteSysOptions(@PathVariable Long id) {
        log.debug("REST request to delete SysOptions : {}", id);
        sysOptionsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
