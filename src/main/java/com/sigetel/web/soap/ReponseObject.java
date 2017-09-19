package com.sigetel.web.soap;

import java.util.Map;

/**
 * Created by mumarm45 on 10/09/2017.
 */
public class ReponseObject {
    private Boolean tokenVerified;

    public Boolean getTokenVerified() {
        return tokenVerified;
    }

    public void setTokenVerified(Boolean tokenVerified) {
        this.tokenVerified = tokenVerified;
    }

    private Map<String, String> mapObject;

    public Map<String, String> getMapObject() {
        return mapObject;
    }

    public void setMapObject(Map<String, String> mapObject) {
        this.mapObject = mapObject;
    }
}
