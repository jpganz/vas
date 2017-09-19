package com.sigetel.web.domain;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.Map;

@XmlRootElement
public class OptionMap {

    private Map<String, String> optionsMap = new HashMap<String, String>();

    public Map<String, String> getOptionsMap() {
        return optionsMap;
    }

    public void setOptionsMap(Map<String, String> optionsMap) {
        this.optionsMap = optionsMap;
    }
}
