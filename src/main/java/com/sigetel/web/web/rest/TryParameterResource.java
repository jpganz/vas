package com.sigetel.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sigetel.web.domain.TryParameter;
import com.sigetel.web.service.TryParameterService;
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
 * REST controller for managing TryParameter.
 */
@RestController
@RequestMapping("/api")
public class TryParameterResource {

    private final Logger log = LoggerFactory.getLogger(TryParameterResource.class);

    private static final String ENTITY_NAME = "tryParameter";

    private final TryParameterService tryParameterService;

    public TryParameterResource(TryParameterService tryParameterService) {
        this.tryParameterService = tryParameterService;
    }

    /**
     * POST  /try-parameters : Create a new tryParameter.
     *
     * @param tryParameter the tryParameter to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tryParameter, or with status 400 (Bad Request) if the tryParameter has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/try-parameters")
    @Timed
    public ResponseEntity<TryParameter> createTryParameter(@Valid @RequestBody TryParameter tryParameter) throws URISyntaxException {
        log.debug("REST request to save TryParameter : {}", tryParameter);
        if (tryParameter.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tryParameter cannot already have an ID")).body(null);
        }
        TryParameter result = tryParameterService.save(tryParameter);
        return ResponseEntity.created(new URI("/api/try-parameters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /try-parameters : Updates an existing tryParameter.
     *
     * @param tryParameter the tryParameter to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tryParameter,
     * or with status 400 (Bad Request) if the tryParameter is not valid,
     * or with status 500 (Internal Server Error) if the tryParameter couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/try-parameters")
    @Timed
    public ResponseEntity<TryParameter> updateTryParameter(@Valid @RequestBody TryParameter tryParameter) throws URISyntaxException {
        log.debug("REST request to update TryParameter : {}", tryParameter);
        if (tryParameter.getId() == null) {
            return createTryParameter(tryParameter);
        }
        TryParameter result = tryParameterService.save(tryParameter);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tryParameter.getId().toString()))
            .body(result);
    }

    /**
     * GET  /try-parameters : get all the tryParameters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tryParameters in body
     */
    @GetMapping("/try-parameters")
    @Timed
    public List<TryParameter> getAllTryParameters() {
        log.debug("REST request to get all TryParameters");
        return tryParameterService.findAll();
    }

    /**
     * GET  /try-parameters/:id : get the "id" tryParameter.
     *
     * @param id the id of the tryParameter to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tryParameter, or with status 404 (Not Found)
     */
    @GetMapping("/try-parameters/{id}")
    @Timed
    public ResponseEntity<TryParameter> getTryParameter(@PathVariable Long id) {
        log.debug("REST request to get TryParameter : {}", id);
        TryParameter tryParameter = tryParameterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tryParameter));
    }

    /**
     * DELETE  /try-parameters/:id : delete the "id" tryParameter.
     *
     * @param id the id of the tryParameter to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/try-parameters/{id}")
    @Timed
    public ResponseEntity<Void> deleteTryParameter(@PathVariable Long id) {
        log.debug("REST request to delete TryParameter : {}", id);
        tryParameterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
