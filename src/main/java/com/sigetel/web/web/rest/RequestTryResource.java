package com.sigetel.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sigetel.web.domain.RequestTry;
import com.sigetel.web.service.RequestTryService;
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
 * REST controller for managing RequestTry.
 */
@RestController
@RequestMapping("/api")
public class RequestTryResource {

    private final Logger log = LoggerFactory.getLogger(RequestTryResource.class);

    private static final String ENTITY_NAME = "requestTry";

    private final RequestTryService requestTryService;

    public RequestTryResource(RequestTryService requestTryService) {
        this.requestTryService = requestTryService;
    }

    /**
     * POST  /request-tries : Create a new requestTry.
     *
     * @param requestTry the requestTry to create
     * @return the ResponseEntity with status 201 (Created) and with body the new requestTry, or with status 400 (Bad Request) if the requestTry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/request-tries")
    @Timed
    public ResponseEntity<RequestTry> createRequestTry(@RequestBody RequestTry requestTry) throws URISyntaxException {
        log.debug("REST request to save RequestTry : {}", requestTry);
        if (requestTry.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new requestTry cannot already have an ID")).body(null);
        }
        RequestTry result = requestTryService.save(requestTry);
        return ResponseEntity.created(new URI("/api/request-tries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /request-tries : Updates an existing requestTry.
     *
     * @param requestTry the requestTry to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated requestTry,
     * or with status 400 (Bad Request) if the requestTry is not valid,
     * or with status 500 (Internal Server Error) if the requestTry couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/request-tries")
    @Timed
    public ResponseEntity<RequestTry> updateRequestTry(@RequestBody RequestTry requestTry) throws URISyntaxException {
        log.debug("REST request to update RequestTry : {}", requestTry);
        if (requestTry.getId() == null) {
            return createRequestTry(requestTry);
        }
        RequestTry result = requestTryService.save(requestTry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, requestTry.getId().toString()))
            .body(result);
    }

    /**
     * GET  /request-tries : get all the requestTries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of requestTries in body
     */
    @GetMapping("/request-tries")
    @Timed
    public List<RequestTry> getAllRequestTries() {
        log.debug("REST request to get all RequestTries");
        return requestTryService.findAll();
    }

    /**
     * GET  /request-tries/:id : get the "id" requestTry.
     *
     * @param id the id of the requestTry to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the requestTry, or with status 404 (Not Found)
     */
    @GetMapping("/request-tries/{id}")
    @Timed
    public ResponseEntity<RequestTry> getRequestTry(@PathVariable Long id) {
        log.debug("REST request to get RequestTry : {}", id);
        RequestTry requestTry = requestTryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(requestTry));
    }

    /**
     * DELETE  /request-tries/:id : delete the "id" requestTry.
     *
     * @param id the id of the requestTry to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/request-tries/{id}")
    @Timed
    public ResponseEntity<Void> deleteRequestTry(@PathVariable Long id) {
        log.debug("REST request to delete RequestTry : {}", id);
        requestTryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
