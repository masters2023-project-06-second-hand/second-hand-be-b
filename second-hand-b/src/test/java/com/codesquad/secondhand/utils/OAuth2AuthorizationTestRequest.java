package com.codesquad.secondhand.utils;

import groovy.transform.builder.Builder;
import java.util.Set;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationResponseType;

public class OAuth2AuthorizationTestRequest {

    private final String authorizationUri;
    private final OAuth2AuthorizationResponseType responseType;
    private final String clientId;
    private final String redirectUri;
    private final Set<String> scopes;
    private final String state;

    @Builder
    public OAuth2AuthorizationTestRequest(String authorizationUri, OAuth2AuthorizationResponseType responseType,
            String clientId, String redirectUri, Set<String> scopes, String state) {
        this.authorizationUri = authorizationUri;
        this.responseType = responseType;
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.scopes = scopes;
        this.state = state;
    }

    public String getAuthorizationUri() {
        return authorizationUri;
    }

    public OAuth2AuthorizationResponseType getResponseType() {
        return responseType;
    }

    public String getClientId() {
        return clientId;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public Set<String> getScopes() {
        return scopes;
    }

    public String getState() {
        return state;
    }
}
