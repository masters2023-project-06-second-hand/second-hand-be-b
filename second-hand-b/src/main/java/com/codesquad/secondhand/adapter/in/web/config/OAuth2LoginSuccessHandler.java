package com.codesquad.secondhand.adapter.in.web.config;

import com.codesquad.secondhand.application.port.in.AuthUseCase;
import com.codesquad.secondhand.application.port.in.exception.NotRegisteredMemberException;
import com.codesquad.secondhand.application.port.in.response.Tokens;
import com.codesquad.secondhand.domain.units.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    AuthUseCase authUseCase;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;
            String email = authenticationToken.getPrincipal().getAttribute("email");

            try {
                Tokens accessToken = authUseCase.signIn(email);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                PrintWriter out = response.getWriter();
                out.write(objectMapper.writeValueAsString(accessToken));
                out.flush();
                out.close();
                return;
            } catch (NotRegisteredMemberException exception) {
                String signUpToken = JwtTokenProvider.createSignUpToken(email);
                PrintWriter out = response.getWriter();
                String signUpToken1 = objectMapper.writeValueAsString(Map.of("signUpToken", signUpToken));
                out.write(signUpToken1);
                out.flush();
                out.close();
                return;
            }
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
