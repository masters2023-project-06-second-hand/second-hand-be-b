package com.codesquad.secondhand.utils;

public class AccessTokenRequest {

    private String code;
    private String client_id;
    private String client_secret;

    public AccessTokenRequest(String code, String client_id, String client_secret) {
        this.code = code;
        this.client_id = client_id;
        this.client_secret = client_secret;
    }

    public AccessTokenRequest() {
    }

    public String getCode() {
        return code;
    }

    public String getClient_id() {
        return client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }
}
