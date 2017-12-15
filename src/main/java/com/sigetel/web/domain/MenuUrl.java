package com.sigetel.web.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * Menu Url used for UI to secure views.
 */
@Entity
@Table(name = "menu_url")

public class MenuUrl implements Serializable {

    private static final long serialVersionUID = 1L;

    public MenuUrl() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("authority")
    private String authority;

    @JsonProperty("path")
    private String path;

    public MenuUrl(String authority, String path) {
        this.authority = authority;
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MenuUrl{" +
            "id=" + getId() +
            "authority=" + getAuthority() +
            ", path='" + getPath() + "'" +
            "}";
    }
}
