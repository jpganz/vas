package com.sigetel.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sigetel.web.domain.ResponseParameter;
import com.sigetel.web.service.ResponseParameterService;
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
 * REST controller for managing ResponseParameter.
 */
@RestController
@RequestMapping("/api")
public class ResponseParameterResource {

    private final Logger log = LoggerFactory.getLogger(ResponseParameterResource.class);

    private static final String ENTITY_NAME = "responseParameter";

    private final ResponseParameterService responseParameterService;

    public ResponseParameterResource(ResponseParameterService responseParameterService) {
        this.responseParameterService = responseParameterService;
    }

    /**
     * POST  /response-parameters : Create a new responseParameter.
     *
     * @param responseParameter the responseParameter to create
     * @return the ResponseEntity with status 201 (Created) and with body the new responseParameter, or with status 400 (Bad Request) if the responseParameter has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/response-parameters")
    @Timed
    public ResponseEntity<ResponseParameter> createResponseParameter(@Valid @RequestBody ResponseParameter responseParameter) throws URISyntaxException {
        log.debug("REST request to save ResponseParameter : {}", responseParameter);
        if (responseParameter.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new responseParameter cannot already have an ID")).body(null);
        }
        ResponseParameter result = responseParameterService.save(responseParameter);
        return ResponseEntity.created(new URI("/api/response-parameters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /response-parameters : Updates an existing responseParameter.
     *
     * @param responseParameter the responseParameter to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated responseParameter,
     * or with status 400 (Bad Request) if the responseParameter is not valid,
     * or with status 500 (Internal Server Error) if the responseParameter couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/response-parameters")
    @Timed
    public ResponseEntity<ResponseParameter> updateResponseParameter(@Valid @RequestBody ResponseParameter responseParameter) throws URISyntaxException {
        log.debug("REST request to update ResponseParameter : {}", responseParameter);
        if (responseParameter.getId() == null) {
            return createResponseParameter(responseParameter);
        }
        ResponseParameter result = responseParameterService.save(responseParameter);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, responseParameter.getId().toString()))
            .body(result);
    }

    /**
     * GET  /response-parameters : get all the responseParameters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of responseParameters in body
     */
    @GetMapping("/response-parameters")
    @Timed
    public List<ResponseParameter> getAllResponseParameters() {
        log.debug("REST request to get all ResponseParameters");
        return responseParameterService.findAll();
    }

    /**
     * GET  /response-parameters/:id : get the "id" responseParameter.
     *
     * @param id the id of the responseParameter to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the responseParameter, or with status 404 (Not Found)
     */
    @GetMapping("/response-parameters/{id}")
    @Timed
    public ResponseEntity<ResponseParameter> getResponseParameter(@PathVariable Long id) {
        log.debug("REST request to get ResponseParameter : {}", id);
        ResponseParameter responseParameter = responseParameterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(responseParameter));
    }

    /**
     * DELETE  /response-parameters/:id : delete the "id" responseParameter.
     *
     * @param id the id of the responseParameter to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/response-parameters/{id}")
    @Timed
    public ResponseEntity<Void> deleteResponseParameter(@PathVariable Long id) {
        log.debug("REST request to delete ResponseParameter : {}", id);
        responseParameterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
