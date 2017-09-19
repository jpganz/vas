package com.sigetel.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sigetel.web.domain.CommunicationStandard;
import com.sigetel.web.service.CommunicationStandardService;
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
 * REST controller for managing CommunicationStandard.
 */
@RestController
@RequestMapping("/api")
public class CommunicationStandardResource {

    private final Logger log = LoggerFactory.getLogger(CommunicationStandardResource.class);

    private static final String ENTITY_NAME = "communicationStandard";

    private final CommunicationStandardService communicationStandardService;

    public CommunicationStandardResource(CommunicationStandardService communicationStandardService) {
        this.communicationStandardService = communicationStandardService;
    }

    /**
     * POST  /communication-standards : Create a new communicationStandard.
     *
     * @param communicationStandard the communicationStandard to create
     * @return the ResponseEntity with status 201 (Created) and with body the new communicationStandard, or with status 400 (Bad Request) if the communicationStandard has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/communication-standards")
    @Timed
    public ResponseEntity<CommunicationStandard> createCommunicationStandard(@Valid @RequestBody CommunicationStandard communicationStandard) throws URISyntaxException {
        log.debug("REST request to save CommunicationStandard : {}", communicationStandard);
        if (communicationStandard.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new communicationStandard cannot already have an ID")).body(null);
        }
        CommunicationStandard result = communicationStandardService.save(communicationStandard);
        return ResponseEntity.created(new URI("/api/communication-standards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /communication-standards : Updates an existing communicationStandard.
     *
     * @param communicationStandard the communicationStandard to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated communicationStandard,
     * or with status 400 (Bad Request) if the communicationStandard is not valid,
     * or with status 500 (Internal Server Error) if the communicationStandard couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/communication-standards")
    @Timed
    public ResponseEntity<CommunicationStandard> updateCommunicationStandard(@Valid @RequestBody CommunicationStandard communicationStandard) throws URISyntaxException {
        log.debug("REST request to update CommunicationStandard : {}", communicationStandard);
        if (communicationStandard.getId() == null) {
            return createCommunicationStandard(communicationStandard);
        }
        CommunicationStandard result = communicationStandardService.save(communicationStandard);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, communicationStandard.getId().toString()))
            .body(result);
    }

    /**
     * GET  /communication-standards : get all the communicationStandards.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of communicationStandards in body
     */
    @GetMapping("/communication-standards")
    @Timed
    public List<CommunicationStandard> getAllCommunicationStandards() {
        log.debug("REST request to get all CommunicationStandards");
        return communicationStandardService.findAll();
    }

    /**
     * GET  /communication-standards/:id : get the "id" communicationStandard.
     *
     * @param id the id of the communicationStandard to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the communicationStandard, or with status 404 (Not Found)
     */
    @GetMapping("/communication-standards/{id}")
    @Timed
    public ResponseEntity<CommunicationStandard> getCommunicationStandard(@PathVariable Long id) {
        log.debug("REST request to get CommunicationStandard : {}", id);
        CommunicationStandard communicationStandard = communicationStandardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(communicationStandard));
    }

    /**
     * DELETE  /communication-standards/:id : delete the "id" communicationStandard.
     *
     * @param id the id of the communicationStandard to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/communication-standards/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommunicationStandard(@PathVariable Long id) {
        log.debug("REST request to delete CommunicationStandard : {}", id);
        communicationStandardService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
