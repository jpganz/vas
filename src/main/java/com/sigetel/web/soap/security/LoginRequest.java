package com.sigetel.web.soap.security;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by mumarm45 on 11/09/2017.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "loginRequest", namespace="http://service.ws.samp")
public class LoginRequest {
    @XmlElement(name = "username", required = true)
    private String username;
    @XmlElement(name = "password", required = true)
   private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
