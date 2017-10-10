package com.sigetel.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sigetel.web.domain.*;
import com.sigetel.web.repository.CommunicationStandardRepository;
import com.sigetel.web.repository.ProviderRepository;
import com.sigetel.web.service.*;
import com.sigetel.web.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Service;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.*;

/**
 * REST controller for managing ProviderCommand.
 */
@RestController
@RequestMapping("/api")
public class ProviderCommandResource {

    private final Logger log = LoggerFactory.getLogger(ProviderCommandResource.class);

    private static final String ENTITY_NAME = "providerCommand";

    private final ProviderCommandService providerCommandService;

    private final ProviderService providerService;

    private final CommunicationStandardService communicationStandardService;

    private final CommandService commandService;

    private final ProviderResponseService providerResponseService;

    private final RequestParameterService requestParameterService;

    private final ServiceSecurityService serviceSecurityService;

    private final SecurityParamsService securityParamsService;

    private final ResponseParameterService responseParameterService;

    public ProviderCommandResource(ProviderCommandService providerCommandService,
                                   ProviderService providerService,
                                   CommunicationStandardService communicationStandardService,
                                   CommandService commandService,
                                   ProviderResponseService providerResponseService,
                                   RequestParameterService requestParameterService,
                                   ServiceSecurityService serviceSecurityService,
                                   SecurityParamsService securityParamsService,
                                   ResponseParameterService responseParameterService) {
        this.providerCommandService = providerCommandService;
        this.providerService = providerService;
        this.communicationStandardService = communicationStandardService;
        this.commandService = commandService;
        this.providerResponseService = providerResponseService;
        this.requestParameterService = requestParameterService;
        this.serviceSecurityService = serviceSecurityService;
        this.securityParamsService = securityParamsService;
        this.responseParameterService = responseParameterService;
    }

    /**
     * POST  /provider-commands : Create a new providerCommand.
     *
     * @param providerCommand the providerCommand to create
     * @return the ResponseEntity with status 201 (Created) and with body the new providerCommand, or with status 400 (Bad Request) if the providerCommand has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/provider-commands")
    @Timed
    @Transactional(readOnly = false)
    public ResponseEntity<ProviderCommand> createProviderCommand(@Valid @RequestBody ProviderCommand providerCommand) throws URISyntaxException {
        log.debug("REST request to save ProviderCommand : {}", providerCommand);
        if (providerCommand.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new providerCommand cannot already have an ID")).body(null);
        }
        //check nested info and then save
        Provider provider = providerCommand.getProvider();
        if (provider.getId() != null) {
            //get from db
            providerCommand.setProvider(providerService.findOne(provider.getId()));
        } else {
            //este si puede ser null
        }

        CommunicationStandard communicationStandard = providerCommand.getCommunicationStandard();
        if (communicationStandard.getId() != null) {
            providerCommand.setCommunicationStandard(communicationStandardService.findOne(communicationStandard.getId()));

            //providerCommand.setCommunicationStandard(null);
        }
        Command command = providerCommand.getCommand();
        if (command.getId() != null) {
            providerCommand.setCommand(commandService.findOne(command.getId()));
        }

        Set<SecurityParams> securityParams = new HashSet<>();
        ServiceSecurity serviceSec =  serviceSecurityService.findOne(providerCommand.getServiceSecurity().getId());
        if(providerCommand.getServiceSecurity().getSecurityParams() != null && providerCommand.getServiceSecurity().getSecurityParams().size() > 0){
            for(SecurityParams paramSec :providerCommand.getServiceSecurity().getSecurityParams()){
                securityParams.add(paramSec);
            }
        }

        ServiceSecurity serviceSecurity = providerCommand.getServiceSecurity();
        if (serviceSecurity.getId() != null) {
            //serviceSecurityService.save(serviceSecurity);
            providerCommand.setServiceSecurity(serviceSecurityService.findOne(serviceSecurity.getId()));
        }


        ProviderCommand savedCommand = providerCommandService.save(providerCommand);

        for(SecurityParams param:securityParams){
            param.setServiceSecurity(serviceSec);
            param.setProviderCommand(savedCommand);
            securityParamsService.save(param);
        }

        Set<ProviderResponse> providerResponses = providerCommand.getProviderResponses();
        if (providerResponses != null && providerResponses.size() > 0) {
            for (ProviderResponse providerResponse : providerResponses) {
                providerResponse.setProviderCommand(savedCommand);
                providerResponseService.save(providerResponse);
            }
        }

        Set<RequestParameter> requestParameters = providerCommand.getRequestParameters();
        if (requestParameters != null && requestParameters.size() > 0) {
            for (RequestParameter requestParameter : requestParameters) {
                requestParameter.setProviderCommand(savedCommand);
                requestParameterService.save(requestParameter);
            }
        }



        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, providerCommand.getId().toString()))
            .body(null);

    }

    /**
     * PUT  /provider-commands : Updates an existing providerCommand.
     *
     * @param providerCommand the providerCommand to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated providerCommand,
     * or with status 400 (Bad Request) if the providerCommand is not valid,
     * or with status 500 (Internal Server Error) if the providerCommand couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/provider-commands")
    @Timed
    public ResponseEntity<ProviderCommand> updateProviderCommand(@Valid @RequestBody ProviderCommand providerCommand) throws URISyntaxException {
        log.debug("REST request to update ProviderCommand : {}", providerCommand);
        if (providerCommand.getId() == null) {
            return createProviderCommand(providerCommand);
        }

        //check nested info and then save
        Provider provider = providerCommand.getProvider();
        if (provider.getId() != null) {
            //get from db
            providerCommand.setProvider(providerService.findOne(provider.getId()));
        } else {
            //este si puede ser null
        }

        CommunicationStandard communicationStandard = providerCommand.getCommunicationStandard();
        if (communicationStandard.getId() != null) {
            providerCommand.setCommunicationStandard(communicationStandardService.findOne(communicationStandard.getId()));

            //providerCommand.setCommunicationStandard(null);
        }
        Command command = providerCommand.getCommand();
        if (command.getId() != null) {
            providerCommand.setCommand(commandService.findOne(command.getId()));
        }

        ProviderCommand currentCommand = providerCommandService.findOne(command.getId());
        ServiceSecurity serviceSecurity = providerCommand.getServiceSecurity();
        if (serviceSecurity.getId() != null) {
            ServiceSecurity serviceSec = serviceSecurityService.findOne(serviceSecurity.getId());
            //serviceSecurityService.save(serviceSecurity);
            Set<SecurityParams> params = providerCommand.getServiceSecurity().getSecurityParams();
            for(SecurityParams param: params){
                param.setServiceSecurity(serviceSecurity);
                param.setProviderCommand(currentCommand);
                param.setServiceSecurity(serviceSec);
                securityParamsService.save(param);
            }

        }

        //providerCommand.setServiceSecurity(serviceSecurityService.findOne(serviceSecurity.getId()));
        ProviderCommand savedCommand = providerCommandService.save(providerCommand);

        Set<ProviderResponse> providerResponses = providerCommand.getProviderResponses();
        if (providerResponses != null && providerResponses.size() > 0) {
            for (ProviderResponse providerResponse : providerResponses) {
                providerResponse.setProviderCommand(savedCommand);
                providerResponseService.save(providerResponse);
            }
        }

        Set<RequestParameter> requestParameters = providerCommand.getRequestParameters();
        if (requestParameters != null && requestParameters.size() > 0) {
            for (RequestParameter requestParameter : requestParameters) {
                requestParameter.setProviderCommand(savedCommand);
                requestParameterService.save(requestParameter);
            }
        }


        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, providerCommand.getId().toString()))
            .body(null);
    }

    /**
     * GET  /provider-commands : get all the providerCommands.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of providerCommands in body
     */
    @GetMapping("/provider-commands")
    @Transactional
    public List<ProviderCommand> getAllProviderCommands() {
        List<ProviderCommand> commands = providerCommandService.findAll();
        List<ProviderCommand> fakeProviders = new ArrayList<>();
        for (final ProviderCommand command:commands){
            final ProviderCommand pCommand = getLocalProviderCommand(command.getId());
            for(SecurityParams param:command.getSecurityParams()){
                param.getId();
            }
            for(SecurityParams param:command.getServiceSecurity().getSecurityParams()){
                param.getId();
            }
            fakeProviders.add(pCommand);
//            break;
        }
        return fakeProviders;
    }

    private ProviderCommand getLocalProviderCommand(Long id) {
        log.debug("REST request to get ProviderCommand : {}", id);
        ProviderCommand providerCommand = providerCommandService.findOne(id);
        Set<RequestParameter> requestParameters = new HashSet<RequestParameter>(requestParameterService.findByAllProviderCommandId(providerCommand.getId()));
        providerCommand.setRequestParameters(requestParameters);
        /*-for (RequestParameter parameter : providerCommand.getRequestParameters()) {
            parameter.getId();
        }*/
        for (ProviderResponse response : providerCommand.getProviderResponses()) {
            response.getId();
        }

        providerCommand.getServiceSecurity().getId();
        Set<SecurityParams> securityParams = new HashSet<>(securityParamsService.findByProviderCommandId(providerCommand.getId()));
        providerCommand.getServiceSecurity().setSecurityParams(securityParams);

        /*for(SecurityParams  securityParams:providerCommand.getServiceSecurity().getSecurityParams()){
            securityParams.getId();
        }*/
       return providerCommand;
    }

    /**
     * GET  /provider-commands/:id : get the "id" providerCommand.
     *
     * @param id the id of the providerCommand to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the providerCommand, or with status 404 (Not Found)
     */
    @GetMapping("/provider-commands/{id}")
    @Timed
    @Transactional
    public ResponseEntity<ProviderCommand> getProviderCommand(@PathVariable Long id) {
        log.debug("REST request to get ProviderCommand : {}", id);
        ProviderCommand providerCommand = providerCommandService.findOne(id);
        Set<RequestParameter> requestParameters = new HashSet<RequestParameter>(requestParameterService.findByAllProviderCommandId(providerCommand.getId()));
        providerCommand.setRequestParameters(requestParameters);
        /*-for (RequestParameter parameter : providerCommand.getRequestParameters()) {
            parameter.getId();
        }*/
        for (ProviderResponse response : providerCommand.getProviderResponses()) {
            response.getId();
        }

        providerCommand.getServiceSecurity().getId();
        Set<SecurityParams> securityParams = new HashSet<>(securityParamsService.findByProviderCommandId(providerCommand.getId()));
        providerCommand.getServiceSecurity().setSecurityParams(securityParams);

        /*for(SecurityParams  securityParams:providerCommand.getServiceSecurity().getSecurityParams()){
            securityParams.getId();
        }*/
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(providerCommand));
    }

    @GetMapping("/provider-commands/provider/{id}")
    @Timed
    @Transactional
    public List<ProviderCommand> getProviderCommandByProvider(@PathVariable Long id) {
        log.debug("REST request to get ProviderCommand : {}", id);
        List<ProviderCommand> commands = providerCommandService.findByProviderId(id);
        for (ProviderCommand providerCommand : commands) {
            Set<RequestParameter> requestParameters = new HashSet<RequestParameter>(requestParameterService.findByAllProviderCommandId(providerCommand.getId()));
            providerCommand.setRequestParameters(requestParameters);

            Set<ProviderResponse> providerResponses = new HashSet<ProviderResponse>(providerResponseService.findByAllProviderCommandId(providerCommand.getId()));
            providerCommand.setProviderResponses(providerResponses);
            for(SecurityParams securityParams:providerCommand.getServiceSecurity().getSecurityParams()){
                securityParams.getId();
            }
        }


        return commands;
    }


    /**
     * DELETE  /provider-commands/:id : delete the "id" providerCommand.
     *
     * @param id the id of the providerCommand to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/provider-commands/{id}")
    @Timed
    @Transactional
    public ResponseEntity<Void> deleteProviderCommand(@PathVariable Long id) {
        log.debug("REST request to delete ProviderCommand : {}", id);
        List<RequestParameter> params = requestParameterService.findByAllProviderCommandId(id);

        List<ProviderResponse> responses = providerResponseService.findByAllProviderCommandId(id);
        for(RequestParameter param:params){
            requestParameterService.delete(param.getId());
        }
        for(ProviderResponse response:responses){
            for(ResponseParameter responseParameter:response.getResponseParameters()){
                responseParameterService.delete(responseParameter.getId());
            }
            providerResponseService.delete(response.getId());
        }

        for(SecurityParams securityParams:securityParamsService.findByProviderCommandId(id)){
            securityParamsService.delete(securityParams.getId());
        }
        providerCommandService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
