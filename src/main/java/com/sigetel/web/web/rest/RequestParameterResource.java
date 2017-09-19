package com.sigetel.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sigetel.web.domain.RequestParameter;
import com.sigetel.web.service.RequestParameterService;
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
 * REST controller for managing RequestParameter.
 */
@RestController
@RequestMapping("/api")
public class RequestParameterResource {

    private final Logger log = LoggerFactory.getLogger(RequestParameterResource.class);

    private static final String ENTITY_NAME = "requestParameter";

    private final RequestParameterService requestParameterService;

    public RequestParameterResource(RequestParameterService requestParameterService) {
        this.requestParameterService = requestParameterService;
    }

    /**
     * POST  /request-parameters : Create a new requestParameter.
     *
     * @param requestParameter the requestParameter to create
     * @return the ResponseEntity with status 201 (Created) and with body the new requestParameter, or with status 400 (Bad Request) if the requestParameter has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/request-parameters")
    @Timed
    public ResponseEntity<RequestParameter> createRequestParameter(@Valid @RequestBody RequestParameter requestParameter) throws URISyntaxException {
        log.debug("REST request to save RequestParameter : {}", requestParameter);
        if (requestParameter.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new requestParameter cannot already have an ID")).body(null);
        }
        RequestParameter result = requestParameterService.save(requestParameter);
        return ResponseEntity.created(new URI("/api/request-parameters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /request-parameters : Updates an existing requestParameter.
     *
     * @param requestParameter the requestParameter to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated requestParameter,
     * or with status 400 (Bad Request) if the requestParameter is not valid,
     * or with status 500 (Internal Server Error) if the requestParameter couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/request-parameters")
    @Timed
    public ResponseEntity<RequestParameter> updateRequestParameter(@Valid @RequestBody RequestParameter requestParameter) throws URISyntaxException {
        log.debug("REST request to update RequestParameter : {}", requestParameter);
        if (requestParameter.getId() == null) {
            return createRequestParameter(requestParameter);
        }
        RequestParameter result = requestParameterService.save(requestParameter);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, requestParameter.getId().toString()))
            .body(result);
    }

    /**
     * GET  /request-parameters : get all the requestParameters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of requestParameters in body
     */
    @GetMapping("/request-parameters")
    @Timed
    public List<RequestParameter> getAllRequestParameters() {
        log.debug("REST request to get all RequestParameters");
        return requestParameterService.findAll();
    }

    /**
     * GET  /request-parameters/:id : get the "id" requestParameter.
     *
     * @param id the id of the requestParameter to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the requestParameter, or with status 404 (Not Found)
     */
    @GetMapping("/request-parameters/{id}")
    @Timed
    public ResponseEntity<RequestParameter> getRequestParameter(@PathVariable Long id) {
        log.debug("REST request to get RequestParameter : {}", id);
        RequestParameter requestParameter = requestParameterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(requestParameter));
    }

    /**
     * DELETE  /request-parameters/:id : delete the "id" requestParameter.
     *
     * @param id the id of the requestParameter to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/request-parameters/{id}")
    @Timed
    public ResponseEntity<Void> deleteRequestParameter(@PathVariable Long id) {
        log.debug("REST request to delete RequestParameter : {}", id);
        requestParameterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
