package com.sigetel.web.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;
import java.util.Objects;

//@JsonDeserialize(as = UserRoleEntity.class)
public  class UserRoleEntity implements  Serializable{

    private static final long serialVersionUID = 1L;

    public UserRoleEntity(@JsonProperty("email") String email,@JsonProperty("role") String role) {
        this.email = email;
        this.role = role;
    }

    @JsonProperty("email")
    private String email;

    @JsonProperty("role")
    private String role;

    @JsonProperty("old_role")
    private String oldRole;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public String getOldRole() {
        return oldRole;
    }

    public void setOldRole(String oldRole) {
        this.oldRole = oldRole;
    }

    @Override
    public boolean equals(Object o) {
      return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getEmail());
    }

    @Override
    public String toString() {
        return "RoleResource{" +
            "email=" + getEmail() +
            ", role='" + getRole() + "'" +
            "}";
    }
}
