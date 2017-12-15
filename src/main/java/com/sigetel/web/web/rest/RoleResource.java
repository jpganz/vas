package com.sigetel.web.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.sigetel.web.domain.Authority;
import com.sigetel.web.domain.User;
import com.sigetel.web.domain.UserRoleEntity;
import com.sigetel.web.repository.AuthorityRepository;
import com.sigetel.web.repository.UserRepository;
import com.sigetel.web.service.MailService;
import com.sigetel.web.service.UserService;
import com.sigetel.web.web.rest.util.HeaderUtil;

@RestController
@RequestMapping("/api")
public class RoleResource {

    private final Logger log = LoggerFactory.getLogger(RoleResource.class);

    private static final String ENTITY_NAME = "userManagement";

    private final UserRepository userRepository;

    private final MailService mailService;

    private final UserService userService;

    private final AuthorityRepository authorityRepository;


    public RoleResource(UserRepository userRepository, MailService mailService,
                        UserService userService,
                        AuthorityRepository authorityRepository) {

        this.userRepository = userRepository;
        this.mailService = mailService;
        this.userService = userService;
        this.authorityRepository = authorityRepository;
    }


    @GetMapping("/users/roles")
    @Timed
    public List<String> getAuthorities() {
        return userService.getAuthorities();
    }

    @PutMapping("/users/roles")
    @Timed
    public ResponseEntity editAuthority(@RequestParam String oldAuthority, @RequestParam String newAuthority) {
        Authority auth = authorityRepository.findByName(oldAuthority);
        Authority newAuth = new Authority(newAuthority);
        if(auth != null){
            authorityRepository.save(newAuth);
            authorityRepository.delete(auth);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Role updated");
    }

    @PostMapping("/users/roles")
    @Timed
    public  ResponseEntity addAuthority(@RequestParam String authority) {

        Authority auth = authorityRepository.findOne(authority);
        if(auth == null){
            Authority newAuth = new Authority(authority);
            //newAuth.setName(authority);
            authorityRepository.save(newAuth);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Role added");
        }
        return ResponseEntity.status(HttpStatus.FOUND).body("Role already found");

    }

    @DeleteMapping("/users/roles")
    @Timed
    public  ResponseEntity deleteRole(@RequestParam String role) {
        Authority auth = authorityRepository.findOne(role);
        if(auth != null){
            Authority newAuth = new Authority(role);
            authorityRepository.delete(newAuth);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Role deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Role not found");
    }


    @DeleteMapping("/users/delete-user-role")
    @Timed
    @Transactional
    public ResponseEntity deleteUserRole(@RequestBody UserRoleEntity userRoleEntity) throws URISyntaxException {
        Optional<User> user = userService.findUserByEmail(userRoleEntity.getEmail());
        Authority authorityToDelete = authorityRepository.findOne(userRoleEntity.getRole());
        if (authorityToDelete == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("role to set not found");
        }
        if(user.isPresent()){
            Set<Authority> newAuthorities = new HashSet<>();
            Set<Authority> authoritiesByUser = user.get().getAuthorities();
            for(Authority authority:authoritiesByUser){
                if(authority.getName() != authorityToDelete.getName()){
                    newAuthorities.add(authority);
                }
            }
            user.get().setAuthorities(newAuthorities);
            userRepository.save(user.get());
            user.get().getAuthorities();
            return ResponseEntity.created(new URI("/users/update-role" + userRoleEntity.getEmail() + "/" + userRoleEntity.getRole()))
                .headers(HeaderUtil.createAlert( "A user was modified with the new authorities", userRoleEntity.getEmail()))
                .body(user.get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }


    @PutMapping("/users/update-role")
    @Timed
    @Transactional
    public ResponseEntity updateRole(@RequestBody UserRoleEntity userRoleEntity) throws URISyntaxException {
        Optional<User> user = userService.findUserByEmail(userRoleEntity.getEmail());
        Authority authorityToUpdate = authorityRepository.findOne(userRoleEntity.getOldRole());
        Authority authorityToAdd = authorityRepository.findOne(userRoleEntity.getRole());
        if (authorityToUpdate == null && authorityToAdd == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("roles to update not found");
        }
        if(user.isPresent()){
            Set<Authority> newAuthorities = new HashSet<>();
            Set<Authority> authoritiesByUser = user.get().getAuthorities();
            for(Authority authority:authoritiesByUser){
                if(authority.getName() != authorityToUpdate.getName()){
                    newAuthorities.add(authority);
                }
            }

            user.get().setAuthorities(newAuthorities);
            userRepository.save(user.get());
            user.get().getAuthorities();
            return ResponseEntity.created(new URI("/users/update-role" + userRoleEntity.getEmail() + "/" + userRoleEntity.getRole()))
                .headers(HeaderUtil.createAlert( "A user was modified with the new authorities", userRoleEntity.getEmail()))
                .body(user.get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PostMapping("/users/add-role")
    @Timed
    @Transactional
    public ResponseEntity addRole(@RequestBody UserRoleEntity userRoleEntity) throws URISyntaxException {
        Optional<User> user = userService.findUserByEmail(userRoleEntity.getEmail());
        Authority authorityToSet = authorityRepository.findOne(userRoleEntity.getRole());
        if (authorityToSet == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("role to set not found");
        }
        if(user.isPresent()){
            Set<Authority> autorities = new HashSet<>();
            autorities.add(authorityToSet);
            Set<Authority> authoritiesByUser = user.get().getAuthorities();
            authoritiesByUser.add(authorityToSet);
            user.get().setAuthorities(authoritiesByUser);
            userRepository.save(user.get());
            user.get().getAuthorities();
            return ResponseEntity.created(new URI("/users/add-role" + userRoleEntity.getEmail() + "/" + userRoleEntity.getRole()))
                .headers(HeaderUtil.createAlert( "A user was modified with the new authorities", userRoleEntity.getEmail()))
                .body(user.get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}
