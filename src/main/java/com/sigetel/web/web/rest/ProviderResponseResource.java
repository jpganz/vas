package com.sigetel.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sigetel.web.domain.ProviderResponse;
import com.sigetel.web.service.ProviderResponseService;
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
 * REST controller for managing ProviderResponse.
 */
@RestController
@RequestMapping("/api")
public class ProviderResponseResource {

    private final Logger log = LoggerFactory.getLogger(ProviderResponseResource.class);

    private static final String ENTITY_NAME = "providerResponse";

    private final ProviderResponseService providerResponseService;

    public ProviderResponseResource(ProviderResponseService providerResponseService) {
        this.providerResponseService = providerResponseService;
    }

    /**
     * POST  /provider-responses : Create a new providerResponse.
     *
     * @param providerResponse the providerResponse to create
     * @return the ResponseEntity with status 201 (Created) and with body the new providerResponse, or with status 400 (Bad Request) if the providerResponse has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/provider-responses")
    @Timed
    public ResponseEntity<ProviderResponse> createProviderResponse(@Valid @RequestBody ProviderResponse providerResponse) throws URISyntaxException {
        log.debug("REST request to save ProviderResponse : {}", providerResponse);
        if (providerResponse.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new providerResponse cannot already have an ID")).body(null);
        }
        ProviderResponse result = providerResponseService.save(providerResponse);
        return ResponseEntity.created(new URI("/api/provider-responses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /provider-responses : Updates an existing providerResponse.
     *
     * @param providerResponse the providerResponse to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated providerResponse,
     * or with status 400 (Bad Request) if the providerResponse is not valid,
     * or with status 500 (Internal Server Error) if the providerResponse couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/provider-responses")
    @Timed
    public ResponseEntity<ProviderResponse> updateProviderResponse(@Valid @RequestBody ProviderResponse providerResponse) throws URISyntaxException {
        log.debug("REST request to update ProviderResponse : {}", providerResponse);
        if (providerResponse.getId() == null) {
            return createProviderResponse(providerResponse);
        }
        ProviderResponse result = providerResponseService.save(providerResponse);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, providerResponse.getId().toString()))
            .body(result);
    }

    /**
     * GET  /provider-responses : get all the providerResponses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of providerResponses in body
     */
    @GetMapping("/provider-responses")
    @Timed
    public List<ProviderResponse> getAllProviderResponses() {
        log.debug("REST request to get all ProviderResponses");
        return providerResponseService.findAll();
    }

    /**
     * GET  /provider-responses/:id : get the "id" providerResponse.
     *
     * @param id the id of the providerResponse to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the providerResponse, or with status 404 (Not Found)
     */
    @GetMapping("/provider-responses/{id}")
    @Timed
    public ResponseEntity<ProviderResponse> getProviderResponse(@PathVariable Long id) {
        log.debug("REST request to get ProviderResponse : {}", id);
        ProviderResponse providerResponse = providerResponseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(providerResponse));
    }

    /**
     * DELETE  /provider-responses/:id : delete the "id" providerResponse.
     *
     * @param id the id of the providerResponse to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/provider-responses/{id}")
    @Timed
    public ResponseEntity<Void> deleteProviderResponse(@PathVariable Long id) {
        log.debug("REST request to delete ProviderResponse : {}", id);
        providerResponseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
