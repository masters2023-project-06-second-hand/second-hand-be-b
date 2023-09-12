package com.codesquad.secondhand.adapter.in.web.config;

import com.codesquad.secondhand.application.port.in.exception.NotRegisteredMemberException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    public static final String EMAIL = "email";
    private final AuthenticationResponseHandler authenticationResponseHandler;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;
            String email = authenticationToken.getPrincipal().getAttribute(EMAIL);

            try {
                authenticationResponseHandler.handleSuccessfulAuthentication(response, email);
                return;
            } catch (NotRegisteredMemberException exception) {
                authenticationResponseHandler.handleNotRegisteredMember(response, email);
                return;
            }
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
