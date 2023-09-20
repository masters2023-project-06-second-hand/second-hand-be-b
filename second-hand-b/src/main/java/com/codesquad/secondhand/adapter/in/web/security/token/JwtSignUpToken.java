package com.codesquad.secondhand.adapter.in.web.security.token;

import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class JwtSignUpToken implements Authentication {

    private final String email;
    private final Collection<? extends GrantedAuthority> authorities;
    private boolean isAuthenticated;

    public JwtSignUpToken(String email, Collection<? extends GrantedAuthority> authorities) {
        this.email = email;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.email;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return email;
    }
}
