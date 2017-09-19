package com.sigetel.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sigetel.web.domain.ProviderCommandRequest;
import com.sigetel.web.service.ProviderCommandRequestService;
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
 * REST controller for managing ProviderCommandRequest.
 */
@RestController
@RequestMapping("/api")
public class ProviderCommandRequestResource {

    private final Logger log = LoggerFactory.getLogger(ProviderCommandRequestResource.class);

    private static final String ENTITY_NAME = "providerCommandRequest";

    private final ProviderCommandRequestService providerCommandRequestService;

    public ProviderCommandRequestResource(ProviderCommandRequestService providerCommandRequestService) {
        this.providerCommandRequestService = providerCommandRequestService;
    }

    /**
     * POST  /provider-command-requests : Create a new providerCommandRequest.
     *
     * @param providerCommandRequest the providerCommandRequest to create
     * @return the ResponseEntity with status 201 (Created) and with body the new providerCommandRequest, or with status 400 (Bad Request) if the providerCommandRequest has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/provider-command-requests")
    @Timed
    public ResponseEntity<ProviderCommandRequest> createProviderCommandRequest(@Valid @RequestBody ProviderCommandRequest providerCommandRequest) throws URISyntaxException {
        log.debug("REST request to save ProviderCommandRequest : {}", providerCommandRequest);
        if (providerCommandRequest.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new providerCommandRequest cannot already have an ID")).body(null);
        }
        ProviderCommandRequest result = providerCommandRequestService.save(providerCommandRequest);
        return ResponseEntity.created(new URI("/api/provider-command-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /provider-command-requests : Updates an existing providerCommandRequest.
     *
     * @param providerCommandRequest the providerCommandRequest to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated providerCommandRequest,
     * or with status 400 (Bad Request) if the providerCommandRequest is not valid,
     * or with status 500 (Internal Server Error) if the providerCommandRequest couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/provider-command-requests")
    @Timed
    public ResponseEntity<ProviderCommandRequest> updateProviderCommandRequest(@Valid @RequestBody ProviderCommandRequest providerCommandRequest) throws URISyntaxException {
        log.debug("REST request to update ProviderCommandRequest : {}", providerCommandRequest);
        if (providerCommandRequest.getId() == null) {
            return createProviderCommandRequest(providerCommandRequest);
        }
        ProviderCommandRequest result = providerCommandRequestService.save(providerCommandRequest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, providerCommandRequest.getId().toString()))
            .body(result);
    }

    /**
     * GET  /provider-command-requests : get all the providerCommandRequests.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of providerCommandRequests in body
     */
    @GetMapping("/provider-command-requests")
    @Timed
    public List<ProviderCommandRequest> getAllProviderCommandRequests() {
        log.debug("REST request to get all ProviderCommandRequests");
        return providerCommandRequestService.findAll();
    }

    /**
     * GET  /provider-command-requests/:id : get the "id" providerCommandRequest.
     *
     * @param id the id of the providerCommandRequest to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the providerCommandRequest, or with status 404 (Not Found)
     */
    @GetMapping("/provider-command-requests/{id}")
    @Timed
    public ResponseEntity<ProviderCommandRequest> getProviderCommandRequest(@PathVariable Long id) {
        log.debug("REST request to get ProviderCommandRequest : {}", id);
        ProviderCommandRequest providerCommandRequest = providerCommandRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(providerCommandRequest));
    }

    /**
     * DELETE  /provider-command-requests/:id : delete the "id" providerCommandRequest.
     *
     * @param id the id of the providerCommandRequest to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/provider-command-requests/{id}")
    @Timed
    public ResponseEntity<Void> deleteProviderCommandRequest(@PathVariable Long id) {
        log.debug("REST request to delete ProviderCommandRequest : {}", id);
        providerCommandRequestService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
