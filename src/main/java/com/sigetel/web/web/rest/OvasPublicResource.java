package com.sigetel.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sigetel.web.config.Constants;
import com.sigetel.web.domain.User;
import com.sigetel.web.repository.UserRepository;
import com.sigetel.web.security.AuthoritiesConstants;
import com.sigetel.web.service.MailService;
import com.sigetel.web.service.UserService;
import com.sigetel.web.service.dto.UserDTO;
import com.sigetel.web.web.rest.util.HeaderUtil;
import com.sigetel.web.web.rest.util.PaginationUtil;
import com.sigetel.web.web.rest.vm.ManagedUserVM;
import com.sigetel.web.web.rest.vm.OvasVM;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing forms.
 * <p>
 * This class accesses the Ovas Resources
 */
@RestController
@RequestMapping("/api")
public class OvasPublicResource {

    private final Logger log = LoggerFactory.getLogger(OvasPublicResource.class);

    private static final String ENTITY_NAME = "userManagement";

    private final UserRepository userRepository;

    private final MailService mailService;

    private final UserService userService;

    public OvasPublicResource(UserRepository userRepository, MailService mailService,
                              UserService userService) {

        this.userRepository = userRepository;
        this.mailService = mailService;
        this.userService = userService;
    }

    /**
     * POST  /ovas-public  : Creates a Ovas Resource Request.
     * <p>
     * Creates a new user if the login and email are not already used, and sends an
     * mail with an activation link.
     * The user needs to be activated on creation.
     *
     * @param OvasVM the request service to create
     * @return the ResponseEntity with status 201 (Created) and with body the new user, or with status 400 (Bad Request) if the login or email is already in use
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ovas-public")
    @PreAuthorize("#oauth2.hasScope('write')")
    @Timed
    public ResponseEntity createRequest(@Valid @RequestBody OvasVM ovasVm) throws URISyntaxException {
        log.debug("REST request to save User : {}", ovasVm);
        if (ovasVm != null){
        return ResponseEntity.ok()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "ovas entry was successfully created"))
                .body(null);
        // Lowercase the user login before comparing with databas
            // todo validacion y volver a guardar las entidades...
        }else{
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "not enough information given"))
                .body(null);
        }
    }


}
