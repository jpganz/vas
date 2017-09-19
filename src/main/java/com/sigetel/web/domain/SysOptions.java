package com.sigetel.web.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SysOptions.
 */
@Entity
@Table(name = "sys_options")
public class SysOptions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "option_name", nullable = false)
    private String option_name;

    @NotNull
    @Column(name = "option_value", nullable = false)
    private String option_value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOption_name() {
        return option_name;
    }

    public SysOptions option_name(String option_name) {
        this.option_name = option_name;
        return this;
    }

    public void setOption_name(String option_name) {
        this.option_name = option_name;
    }

    public String getOption_value() {
        return option_value;
    }

    public SysOptions option_value(String option_value) {
        this.option_value = option_value;
        return this;
    }

    public void setOption_value(String option_value) {
        this.option_value = option_value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysOptions sysOptions = (SysOptions) o;
        if (sysOptions.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sysOptions.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SysOptions{" +
            "id=" + getId() +
            ", option_name='" + getOption_name() + "'" +
            ", option_value='" + getOption_value() + "'" +
            "}";
    }
}
