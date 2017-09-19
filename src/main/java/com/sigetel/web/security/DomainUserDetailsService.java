package com.sigetel.web.security;

import com.sigetel.web.domain.User;
import com.sigetel.web.repository.UserRepository;
import com.sigetel.web.wsdl.AuthTigoApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UserRepository userRepository;

    public DomainUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);
        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        Optional<User> userFromDatabase = userRepository.findOneWithAuthoritiesByLogin(lowercaseLogin);
        return userFromDatabase.map(user -> {
            //if (!isUserValid(userFromDatabase.get().getEmail(), userFromDatabase.get().getPassword()) || !user.getActivated()) {
            if (!user.getActivated()) {
                    throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
            }
            List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
            return new org.springframework.security.core.userdetails.User(lowercaseLogin,
                user.getPassword(),
                grantedAuthorities);
        }).orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the " +
        "database"));
    }

    private boolean isUserValid(final String user,final String password ) {
        AuthTigoApp authTigoApp = new AuthTigoApp();
        authTigoApp.setCredentialsWS("sigel", "sig3lusr");
        //System.out.println("RESPONSE: " +
        return (authTigoApp.processUserInformationResponse(
            authTigoApp.requestUserInfo(100, user, password)).size() > 0);
    }
}
