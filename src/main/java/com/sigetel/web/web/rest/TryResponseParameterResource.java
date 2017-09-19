package com.sigetel.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sigetel.web.domain.TryResponseParameter;
import com.sigetel.web.service.TryResponseParameterService;
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
 * REST controller for managing TryResponseParameter.
 */
@RestController
@RequestMapping("/api")
public class TryResponseParameterResource {

    private final Logger log = LoggerFactory.getLogger(TryResponseParameterResource.class);

    private static final String ENTITY_NAME = "tryResponseParameter";

    private final TryResponseParameterService tryResponseParameterService;

    public TryResponseParameterResource(TryResponseParameterService tryResponseParameterService) {
        this.tryResponseParameterService = tryResponseParameterService;
    }

    /**
     * POST  /try-response-parameters : Create a new tryResponseParameter.
     *
     * @param tryResponseParameter the tryResponseParameter to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tryResponseParameter, or with status 400 (Bad Request) if the tryResponseParameter has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/try-response-parameters")
    @Timed
    public ResponseEntity<TryResponseParameter> createTryResponseParameter(@Valid @RequestBody TryResponseParameter tryResponseParameter) throws URISyntaxException {
        log.debug("REST request to save TryResponseParameter : {}", tryResponseParameter);
        if (tryResponseParameter.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tryResponseParameter cannot already have an ID")).body(null);
        }
        TryResponseParameter result = tryResponseParameterService.save(tryResponseParameter);
        return ResponseEntity.created(new URI("/api/try-response-parameters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /try-response-parameters : Updates an existing tryResponseParameter.
     *
     * @param tryResponseParameter the tryResponseParameter to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tryResponseParameter,
     * or with status 400 (Bad Request) if the tryResponseParameter is not valid,
     * or with status 500 (Internal Server Error) if the tryResponseParameter couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/try-response-parameters")
    @Timed
    public ResponseEntity<TryResponseParameter> updateTryResponseParameter(@Valid @RequestBody TryResponseParameter tryResponseParameter) throws URISyntaxException {
        log.debug("REST request to update TryResponseParameter : {}", tryResponseParameter);
        if (tryResponseParameter.getId() == null) {
            return createTryResponseParameter(tryResponseParameter);
        }
        TryResponseParameter result = tryResponseParameterService.save(tryResponseParameter);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tryResponseParameter.getId().toString()))
            .body(result);
    }

    /**
     * GET  /try-response-parameters : get all the tryResponseParameters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tryResponseParameters in body
     */
    @GetMapping("/try-response-parameters")
    @Timed
    public List<TryResponseParameter> getAllTryResponseParameters() {
        log.debug("REST request to get all TryResponseParameters");
        return tryResponseParameterService.findAll();
    }

    /**
     * GET  /try-response-parameters/:id : get the "id" tryResponseParameter.
     *
     * @param id the id of the tryResponseParameter to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tryResponseParameter, or with status 404 (Not Found)
     */
    @GetMapping("/try-response-parameters/{id}")
    @Timed
    public ResponseEntity<TryResponseParameter> getTryResponseParameter(@PathVariable Long id) {
        log.debug("REST request to get TryResponseParameter : {}", id);
        TryResponseParameter tryResponseParameter = tryResponseParameterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tryResponseParameter));
    }

    /**
     * DELETE  /try-response-parameters/:id : delete the "id" tryResponseParameter.
     *
     * @param id the id of the tryResponseParameter to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/try-response-parameters/{id}")
    @Timed
    public ResponseEntity<Void> deleteTryResponseParameter(@PathVariable Long id) {
        log.debug("REST request to delete TryResponseParameter : {}", id);
        tryResponseParameterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
