package com.sigetel.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sigetel.web.domain.ProviderRequest;
import com.sigetel.web.service.ProviderRequestService;
import com.sigetel.web.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ProviderRequest.
 */
@RestController
@RequestMapping("/api")
public class ProviderRequestResource {

    private final Logger log = LoggerFactory.getLogger(ProviderRequestResource.class);

    private static final String ENTITY_NAME = "providerRequest";

    private final ProviderRequestService providerRequestService;

    public ProviderRequestResource(ProviderRequestService providerRequestService) {
        this.providerRequestService = providerRequestService;
    }

    /**
     * POST  /provider-requests : Create a new providerRequest.
     *
     * @param providerRequest the providerRequest to create
     * @return the ResponseEntity with status 201 (Created) and with body the new providerRequest, or with status 400 (Bad Request) if the providerRequest has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/provider-requests")
    @Timed
    public ResponseEntity<ProviderRequest> createProviderRequest(@RequestBody ProviderRequest providerRequest) throws URISyntaxException {
        log.debug("REST request to save ProviderRequest : {}", providerRequest);
        if (providerRequest.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new providerRequest cannot already have an ID")).body(null);
        }
        ProviderRequest result = providerRequestService.save(providerRequest);
        return ResponseEntity.created(new URI("/api/provider-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /provider-requests : Updates an existing providerRequest.
     *
     * @param providerRequest the providerRequest to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated providerRequest,
     * or with status 400 (Bad Request) if the providerRequest is not valid,
     * or with status 500 (Internal Server Error) if the providerRequest couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/provider-requests")
    @Timed
    public ResponseEntity<ProviderRequest> updateProviderRequest(@RequestBody ProviderRequest providerRequest) throws URISyntaxException {
        log.debug("REST request to update ProviderRequest : {}", providerRequest);
        if (providerRequest.getId() == null) {
            return createProviderRequest(providerRequest);
        }
        ProviderRequest result = providerRequestService.save(providerRequest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, providerRequest.getId().toString()))
            .body(result);
    }

    /**
     * GET  /provider-requests : get all the providerRequests.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of providerRequests in body
     */
    @GetMapping("/provider-requests")
    @Timed
    public List<ProviderRequest> getAllProviderRequests() {
        log.debug("REST request to get all ProviderRequests");
        return providerRequestService.findAll();
    }

    /**
     * GET  /provider-requests/:id : get the "id" providerRequest.
     *
     * @param id the id of the providerRequest to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the providerRequest, or with status 404 (Not Found)
     */
    @GetMapping("/provider-requests/{id}")
    @Timed
    public ResponseEntity<ProviderRequest> getProviderRequest(@PathVariable Long id) {
        log.debug("REST request to get ProviderRequest : {}", id);
        ProviderRequest providerRequest = providerRequestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(providerRequest));
    }

    /**
     * DELETE  /provider-requests/:id : delete the "id" providerRequest.
     *
     * @param id the id of the providerRequest to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/provider-requests/{id}")
    @Timed
    public ResponseEntity<Void> deleteProviderRequest(@PathVariable Long id) {
        log.debug("REST request to delete ProviderRequest : {}", id);
        providerRequestService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
