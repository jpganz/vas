package com.sigetel.web.soap.security;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component

public class AuthService extends LoggingInInterceptor {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private TokenService generateToken;



    public String createAuthToken( LoginRequest userRequest) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            userRequest.getUsername(),userRequest.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(userRequest.getUsername());
        final String token =generateToken.createToken(userDetails);

        return token;
    }



    public boolean checkLogin(String headerToken){
        String username =  generateToken.getUsernameFromToken(headerToken);
        Boolean login = false;

        if(username !=null){

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if(generateToken.validateToken(headerToken,userDetails)){
                login = true;


            }


        }

        return  login;
    }
}
