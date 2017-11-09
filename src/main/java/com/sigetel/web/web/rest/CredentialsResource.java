package com.sigetel.web.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sigetel.web.soap.SoapService;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for view and managing Log Level at runtime.
 */
@RestController
@RequestMapping("/credentials")
public class CredentialsResource {
	
	private final SoapService soapServiceImpl;
	
	public CredentialsResource(SoapService soapService) {
		this.soapServiceImpl = soapService;
	}

    @GetMapping("/validate")
    @Timed
    public boolean getCommand(@RequestParam  final String user, @RequestParam final String password ) {
    	return soapServiceImpl.invokeLdap(user, password);
    }

}
