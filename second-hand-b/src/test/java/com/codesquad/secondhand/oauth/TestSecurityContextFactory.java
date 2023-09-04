package com.codesquad.secondhand.oauth;

import java.util.List;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class TestSecurityContextFactory implements WithSecurityContextFactory<WithTestUser> {

    @Override
    public SecurityContext createSecurityContext(WithTestUser testUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Map<String, Object> attributes = Map.of("name", testUser.username(), "email", testUser.email());

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(testUser.roles());
        OAuth2User user = new DefaultOAuth2User(authorities, attributes, "name");
        OAuth2AuthenticationToken token = new OAuth2AuthenticationToken(user, authorities, "registrationId");
        context.setAuthentication(token);
        return context;
    }
}
