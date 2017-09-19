package com.sigetel.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sigetel.web.domain.ProviderCommand;
import com.sigetel.web.service.ProviderCommandService;
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
 * REST controller for managing ProviderCommand.
 */
@RestController
@RequestMapping("/api")
public class ProviderCommandResource {

    private final Logger log = LoggerFactory.getLogger(ProviderCommandResource.class);

    private static final String ENTITY_NAME = "providerCommand";

    private final ProviderCommandService providerCommandService;

    public ProviderCommandResource(ProviderCommandService providerCommandService) {
        this.providerCommandService = providerCommandService;
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
    public ResponseEntity<ProviderCommand> createProviderCommand(@Valid @RequestBody ProviderCommand providerCommand) throws URISyntaxException {
        log.debug("REST request to save ProviderCommand : {}", providerCommand);
        if (providerCommand.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new providerCommand cannot already have an ID")).body(null);
        }
        ProviderCommand result = providerCommandService.save(providerCommand);
        return ResponseEntity.created(new URI("/api/provider-commands/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
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
        ProviderCommand result = providerCommandService.save(providerCommand);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, providerCommand.getId().toString()))
            .body(result);
    }

    /**
     * GET  /provider-commands : get all the providerCommands.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of providerCommands in body
     */
    @GetMapping("/provider-commands")
    @Timed
    public List<ProviderCommand> getAllProviderCommands() {
        log.debug("REST request to get all ProviderCommands");
        return providerCommandService.findAll();
    }

    /**
     * GET  /provider-commands/:id : get the "id" providerCommand.
     *
     * @param id the id of the providerCommand to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the providerCommand, or with status 404 (Not Found)
     */
    @GetMapping("/provider-commands/{id}")
    @Timed
    public ResponseEntity<ProviderCommand> getProviderCommand(@PathVariable Long id) {
        log.debug("REST request to get ProviderCommand : {}", id);
        ProviderCommand providerCommand = providerCommandService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(providerCommand));
    }

    /**
     * DELETE  /provider-commands/:id : delete the "id" providerCommand.
     *
     * @param id the id of the providerCommand to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/provider-commands/{id}")
    @Timed
    public ResponseEntity<Void> deleteProviderCommand(@PathVariable Long id) {
        log.debug("REST request to delete ProviderCommand : {}", id);
        providerCommandService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
