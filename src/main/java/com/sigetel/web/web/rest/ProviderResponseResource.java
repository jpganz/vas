package com.sigetel.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sigetel.web.domain.*;
import com.sigetel.web.service.ProviderCommandService;
import com.sigetel.web.service.ProviderResponseService;
import com.sigetel.web.service.ResponseParameterService;
import com.sigetel.web.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.*;

/**
 * REST controller for managing ProviderResponse.
 */
@RestController
@RequestMapping("/api")
public class ProviderResponseResource {

    private final Logger log = LoggerFactory.getLogger(ProviderResponseResource.class);

    private static final String ENTITY_NAME = "providerResponse";

    private final ProviderResponseService providerResponseService;

    private final ResponseParameterService responseParameterService;

    private final ProviderCommandService providerCommandService;

    public ProviderResponseResource(ProviderResponseService providerResponseService,
                                    ResponseParameterService responseParameterService,
                                    ProviderCommandService providerCommandService) {
        this.providerResponseService = providerResponseService;
        this.responseParameterService = responseParameterService;
        this.providerCommandService = providerCommandService;
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
    public ResponseEntity<ProviderResponse> createProviderResponse(@Valid @RequestBody ProviderResponseCommand providerResponse) throws URISyntaxException {
        log.debug("REST request to save ProviderResponse : {}", providerResponse);
        if (providerResponse.getProviderResponse().getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new providerResponse cannot already have an ID")).body(null);
        }
        ProviderCommand command = providerCommandService.findOne(providerResponse.getProviderCommand().getId());
        if(command != null && command.getId() > 0){
            ProviderResponse response = providerResponse.getProviderResponse();
            response.setProviderCommand(command);
            providerResponseService.save(response);
            for(ResponseParameter parameter:response.getResponseParameters()){
                parameter.setProviderResponse(response);
                responseParameterService.save(parameter);
            }
        }else{
            //error return ResponseEntity
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(null);
        }
        //ProviderResponse result = providerResponseService.save(providerResponse);
        return ResponseEntity.created(new URI("/api/provider-responses/" + command.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, command.getId().toString()))
            .body(null);
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
        ProviderCommand command = providerResponse.getProviderCommand();
        if(command != null && command.getId() > 0){
            providerResponse.setProviderCommand(providerCommandService.findOne(command.getId()));
            providerResponseService.save(providerResponse);
            for(ResponseParameter parameter:providerResponse.getResponseParameters()){
                parameter.setProviderResponse(providerResponse);
                responseParameterService.save(parameter);
            }
        }else{
            //error return ResponseEntity
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(null);
        }
        //ProviderResponse result = providerResponseService.save(providerResponse);
        return ResponseEntity.created(new URI("/api/provider-responses/" + command.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, command.getId().toString()))
            .body(null);
    }

    /**
     * GET  /provider-responses : get all the providerResponses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of providerResponses in body
     */
    @GetMapping("/provider-responses")
    @Timed
    public List<ProviderResponseCommand> getAllProviderResponses() {
        log.debug("REST request to get all ProviderResponses");
        List<ProviderResponseCommand> responseCommand= new ArrayList<>();
        List<ProviderResponse> responses = providerResponseService.findAll();
        for(ProviderResponse response:responses){
            ProviderResponseCommand providerResponseCommand = new ProviderResponseCommand();
            providerResponseCommand.setProviderResponse(response);
            providerResponseCommand.setProviderCommand(providerCommandService.findOne(response.getProviderCommand().getId()));
            responseCommand.add(providerResponseCommand);
        }
        return responseCommand;
    }

    /**
     * GET  /provider-responses/:id : get the "id" providerResponse.
     *
     * @param id the id of the providerResponse to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the providerResponse, or with status 404 (Not Found)
     */
    @GetMapping("/provider-responses/{id}")
    @Timed
    public ResponseEntity<ProviderResponseCommand> getProviderResponse(@PathVariable Long id) {
        log.debug("REST request to get ProviderResponse : {}", id);
        ProviderResponse response = providerResponseService.findOne(id);
        if(response != null){
            ProviderResponseCommand providerResponseCommand = new ProviderResponseCommand();
            providerResponseCommand.setProviderResponse(response);
            providerResponseCommand.setProviderCommand(providerCommandService.findOne(response.getProviderCommand().getId()));
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(providerResponseCommand));
        }else{
            //return not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(null);
        }
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
