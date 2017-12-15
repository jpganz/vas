package com.sigetel.web.domain;

public class PathModel {

   private Long id;
   private String pathUrl;

    public PathModel(Long id, String pathUrl) {
        this.id = id;
        this.pathUrl = pathUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPathUrl() {
        return pathUrl;
    }

    public void setPathUrl(String pathUrl) {
        this.pathUrl = pathUrl;
    }
}
