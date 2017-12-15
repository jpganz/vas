package com.sigetel.web.domain;

import java.util.Map;

public class SaveRequestCommand {

    Map<String, String> headers;
    Map<String, String> body;
    Map<String, String> auth;
    Request request;
    long protocol;

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getBody() {
        return body;
    }

    public void setBody(Map<String, String> body) {
        this.body = body;
    }

    public Map<String, String> getAuth() {
        return auth;
    }

    public void setAuth(Map<String, String> auth) {
        this.auth = auth;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public long getProtocol() {
        return protocol;
    }

    public void setProtocol(long protocol) {
        this.protocol = protocol;
    }
}
