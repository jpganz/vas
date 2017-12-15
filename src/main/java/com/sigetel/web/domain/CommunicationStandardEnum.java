package com.sigetel.web.domain;

public enum CommunicationStandardEnum {
    REST(1),
    SOAP(2);

    private int value;

    private CommunicationStandardEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
