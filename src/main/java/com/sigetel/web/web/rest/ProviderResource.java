package com.sigetel.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sigetel.web.domain.*;
import com.sigetel.web.service.ProviderService;
import com.sigetel.web.soap.SoapService;
import com.sigetel.web.web.rest.consumer.RestClient;
import com.sigetel.web.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.*;

/**
 * REST controller for managing Provider.
 */
@RestController
@RequestMapping("/api")
public class ProviderResource {

    private final Logger log = LoggerFactory.getLogger(ProviderResource.class);

    private static final String ENTITY_NAME = "provider";

    private final ProviderService providerService;

    private final RestClient restClient;

    private final SoapService soapService;

    public ProviderResource(ProviderService providerService,
                            RestClient restClient,
                            SoapService soapService) {
        this.providerService = providerService;
        this.restClient = restClient;
        this.soapService = soapService;
    }

    /**
     * POST  /providers : Create a new provider.
     *
     * @param provider the provider to create
     * @return the ResponseEntity with status 201 (Created) and with body the new provider, or with status 400 (Bad Request) if the provider has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/providers")
    @Timed
    public ResponseEntity<Provider> createProvider(@Valid @RequestBody Provider provider) throws URISyntaxException {
        log.debug("REST request to save Provider : {}", provider);
        if (provider.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new provider cannot already have an ID")).body(null);
        }
        Provider result = providerService.save(provider);
        return ResponseEntity.created(new URI("/api/providers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /providers : Updates an existing provider.
     *
     * @param provider the provider to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated provider,
     * or with status 400 (Bad Request) if the provider is not valid,
     * or with status 500 (Internal Server Error) if the provider couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/providers")
    @Timed
    public ResponseEntity<Provider> updateProvider( @RequestBody Provider provider) throws URISyntaxException {
        log.debug("REST request to update Provider : {}", provider);
        if (provider.getId() == null) {
            return createProvider(provider);
        }
        Provider result = providerService.save(provider);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, provider.getId().toString()))
            .body(result);
    }


    @GetMapping("/testRetryTime")
    @Timed
    public String testRetryTime() {
        final DateTime nextTry = new DateTime().plusMinutes(50);
        return nextTry.toString();
    }

    @GetMapping("/testRetry")
    @Timed
    public void testRetry() {
        soapService.invokeRetryRequest();
    }

    @GetMapping("/testRest")
    @Timed
    public ResponseEntity<Provider> testRest() {
        Map<String, String> hm = new HashMap<String, String>();
        hm.put("content-type", "application/json");
        String p = "{ \"code\": \"GQ574\", \"price\": 399, \"departureDate\": \"2016/12/20\",\"origin\": \"ORD\", \"destination\": \"SFO\", \"emptySeats\": 200, \"plane\": {\"type\": \"Boeing 747\", \"totalSeats\": 400}}";
        RestClient restClient = new RestClient();
        restClient.postRequest("http://apdev-american-ws.cloudhub.io/api/flights", p,"application/json", hm);
        return null;
    }

    /**
     * GET  /providers/:id : get the "id" provider.
     *
     * @param id the id of the provider to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the provider, or with status 404 (Not Found)
     */
    @GetMapping("/providersx/{id}")
    @Timed
    public ResponseEntity<Provider> getProvider(@PathVariable Long id) {
        log.debug("REST request to get Provider : {}", id);
        Provider provider = providerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(provider));
    }

    /**
     * DELETE  /providers/:id : delete the "id" provider.
     *
     * @param id the id of the provider to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/providers/{id}")
    @Timed
    public ResponseEntity<Void> deleteProvider(@PathVariable Long id) {
        log.debug("REST request to delete Provider : {}", id);
        providerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/providers")
    @Timed
    public List<Provider> getAllProviders0() {
        log.debug("REST request to get all Providers");
        return providerService.findAll();
    }

    @GetMapping("/providers-list/{id}")
    @Timed
    //@Transactional
    public ResponseEntity<Provider> getAllProviders1(@PathVariable Long id) {
        log.debug("REST request to get all Providers");
        Provider provider = providerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(provider));
        /*List<ProviderCommand> providerCommands = provider.getProviderCommands();
        for (ProviderCommand command : providerCommands) {
            command.getId();
            command.getCommand().getName();
            command.getCommunicationStandard().getName();
            for (ServiceSecurity security : command.getServiceSecurities()) {
                security.getName();
                for (SecurityParams params : security.getSecurityParams()) {
                    params.getId();
                   //break;
                }
                //break;
            }
            for (RequestParameter param : command.getRequestParameters()) {
                param.getName();
                //break;
            }
           // break;
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(provider));*/
    }






}
