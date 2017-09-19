package com.sigetel.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sigetel.web.wsdl.AuthTigoApp;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for view and managing Log Level at runtime.
 */
@RestController
@RequestMapping("/credentials")
public class CredentialsResource {

    @GetMapping("/validate")
    @Timed
    public org.json.simple.JSONObject getCommand(@RequestParam
                                                     final String user, @RequestParam final String password ) {
        AuthTigoApp authTigoApp = new AuthTigoApp();
        authTigoApp.setCredentialsWS("sigel", "sig3lusr");
        //System.out.println("RESPONSE: " +
           return authTigoApp.processUserInformationResponse(
                authTigoApp.requestUserInfo(100, user, password));
    }

}
