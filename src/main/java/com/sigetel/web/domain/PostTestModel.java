package com.sigetel.web.domain;

import java.io.Serializable;

public class PostTestModel implements Serializable {

    private static final long serialVersionUID = 1L;

    public PostTestModel() {
    }

    private String name;
    private String nick;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
