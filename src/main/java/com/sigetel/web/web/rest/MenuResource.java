package com.sigetel.web.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.sigetel.web.domain.Authority;
import com.sigetel.web.domain.CommunicationStandard;
import com.sigetel.web.domain.MenuUrl;
import com.sigetel.web.domain.PathModel;
import com.sigetel.web.domain.PostTestModel;
import com.sigetel.web.repository.AuthorityRepository;
import com.sigetel.web.repository.MenuUrlRepository;
import com.sigetel.web.security.AuthoritiesConstants;
import com.sigetel.web.web.rest.util.HeaderUtil;
import com.sigetel.web.web.rest.vm.ManagedUrlCommand;
import com.sigetel.web.web.rest.vm.ManagedUrlVM;

@RestController
@RequestMapping("/api")
public class MenuResource {

    private final Logger log = LoggerFactory.getLogger(MenuResource.class);

    private static final String ENTITY_NAME = "urlManagment";

    //should move to a service
    private final AuthorityRepository authorityRepository;

    private final MenuUrlRepository menuUrlRepository;

    public MenuResource(AuthorityRepository authorityRepository, MenuUrlRepository menuUrlRepository) {
        this.authorityRepository = authorityRepository;
        this.menuUrlRepository = menuUrlRepository;
    }

    /**
     * POST  /pathUrl  : Creates a new path url linked by a role.
     * <p>
     * Creates a new entry that consist in a relation between a role and (String) MenuUrl(s) for the UI
     *
     * @param managedUrl the url to create
     * @return the ResponseEntity with status 201 (Created) and with body the new url, or with status 400 (Bad Request) if the role is already in use
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pathUrl")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    @Transactional
    public ResponseEntity createUser(@Valid @RequestBody ManagedUrlCommand managedUrl) throws URISyntaxException {
        log.debug("REST request to save url : {}", managedUrl);

        if (managedUrl.getRole() == null) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A role must be specified"))
                .body(null);
            // Lowercase the user login before comparing with database
        }
        Authority role = authorityRepository.findByName(managedUrl.getRole());
        if (managedUrl.getRole() == null) {
            return ResponseEntity.notFound()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "not found", "The specified role does not exist"))
                .build();
            // Lowercase the user login before comparing with database
        }

        for (String path : managedUrl.getUrls()) {
            menuUrlRepository.save(new MenuUrl(managedUrl.getRole(), path));
        }
        return ResponseEntity.created(new URI("/pathUrl/"))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, role.getName()))
            .body(menuUrlRepository.findByAuthority(role.getName()));
    }

    /**
     * GET  /pathUrl
     *
     * @return the ResponseEntity with status 200 (OK) and the list of path urls
     */
    @GetMapping("/pathUrl/{role}")
    @Timed
    public List<ManagedUrlVM> getAllByRole(@PathVariable String role) {
        List<MenuUrl> menus = menuUrlRepository.findByAuthority(role);
        Map<String, List<PathModel>> myMap = new HashMap<>();
        List<ManagedUrlVM> managedUrlVMS = new ArrayList<>();
        for(MenuUrl menu:menus){
            myMap.put(menu.getAuthority(),getPathsByRole(menu.getAuthority(), menus));
        }
        for(Map.Entry<String, List<PathModel>> entry : myMap.entrySet()) {
            ManagedUrlVM url = new ManagedUrlVM();
            url.setRole(entry.getKey());
            url.setUrls(entry.getValue());
            managedUrlVMS.add(url);
        }

        System.out.println("test");
        return managedUrlVMS;
    }

    @PutMapping("/pathUrl/")
    @Timed
    public ResponseEntity<CommunicationStandard> updatePathUrl(@Valid @RequestBody MenuUrl menuUrl) throws URISyntaxException {
        if (menuUrl.getId() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "id does not exist", "Id cannot be null")).body(null);
        }
        menuUrlRepository.save(menuUrl);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, menuUrl.getId().toString()))
            .body(null);
    }

    @DeleteMapping("/pathUrl/{pathId}")
    @Timed
    public ResponseEntity<MenuUrl> deleteMenuUrl(@PathVariable Long pathId) {
        if (pathId == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "id does not exist", "Id cannot be null")).body(null);
        }
        menuUrlRepository.delete(menuUrlRepository.findOne(pathId));
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("A configuration is deleted with identifier "
            + pathId, ""))
            .build();
    }

    @PostMapping("/testPost")
    @Timed
    public ResponseEntity<MenuUrl> deleteMenuUrl(@Valid @RequestBody PostTestModel testModel) {
        System.out.println("*****************************************");
        System.out.println(testModel.getName());
        System.out.println(testModel.getNick());
        System.out.println("*****************************************");
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("Successfully tested ", ""))
            .build();
    }


    /**
     * GET  /pathUrl
     *
     * @return the ResponseEntity with status 200 (OK) and the list of path urls
     */
    @GetMapping("/pathUrl")
    @Timed
    public List<ManagedUrlVM> getAllManagedUrl() {
        List<MenuUrl> menus = menuUrlRepository.findAll();
        Map<String, List<PathModel>> myMap = new HashMap<>();
        List<ManagedUrlVM> managedUrlVMS = new ArrayList<>();
        for(MenuUrl menu:menus){
            myMap.put(menu.getAuthority(),getPathsByRole(menu.getAuthority(), menus));
        }
        for(Map.Entry<String, List<PathModel>> entry : myMap.entrySet()) {
            ManagedUrlVM url = new ManagedUrlVM();
            url.setRole(entry.getKey());
            url.setUrls(entry.getValue());
            managedUrlVMS.add(url);
        }

        System.out.println("test");
        return managedUrlVMS;
    }

    private List<PathModel> getPathsByRole(String role, List<MenuUrl> menus){
        List<PathModel> paths = new ArrayList<>();
        for(MenuUrl menu:menus){
            if(menu.getAuthority().equals(role)){
                paths.add(new PathModel(menu.getId(), menu.getPath()));
            }
        }
        return paths;
    }
}
