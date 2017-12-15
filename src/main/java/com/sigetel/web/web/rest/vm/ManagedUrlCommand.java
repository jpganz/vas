package com.sigetel.web.web.rest.vm;

import com.sigetel.web.domain.PathModel;

import java.util.List;


public class ManagedUrlCommand {

    private String role;
    private List<String> urls;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
