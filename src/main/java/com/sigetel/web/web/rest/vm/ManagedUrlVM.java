package com.sigetel.web.web.rest.vm;

import com.sigetel.web.domain.PathModel;
import com.sigetel.web.service.dto.UserDTO;

import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.List;
import java.util.Set;


public class ManagedUrlVM  {

    private String role;
    private List<PathModel> urls;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<PathModel> getUrls() {
        return urls;
    }

    public void setUrls(List<PathModel> urls) {
        this.urls = urls;
    }
}
