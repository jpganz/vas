package com.sigetel.web.domain;


public enum  RequestTryStatus {
    PENDING(0),
    REQUESTED(1),
    TRY_AGAIN(2),
    FAILED(3),
    TESTING(4);

    private int value;

    private RequestTryStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
