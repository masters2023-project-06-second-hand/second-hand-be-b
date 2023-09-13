package com.codesquad.secondhand.adapter.in.web.config;

import com.codesquad.secondhand.application.port.in.AuthUseCase;
import com.codesquad.secondhand.application.port.in.exception.ErrorResponse;
import com.codesquad.secondhand.application.port.in.exception.Errors;
import com.codesquad.secondhand.application.port.in.response.Tokens;
import com.codesquad.secondhand.domain.units.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationResponseHandlerImpl implements AuthenticationResponseHandler {

    public static final String SIGN_UP_TOKEN = "signUpToken";
    private final AuthUseCase authUseCase;
    private final ObjectMapper objectMapper;

    @Override
    public void handleSuccessfulAuthentication(HttpServletResponse response, String email) throws IOException {
        Tokens accessToken = authUseCase.signIn(email);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter out = response.getWriter();
        out.write(objectMapper.writeValueAsString(accessToken));
        out.flush();
        out.close();
    }

    @Override
    public void handleNotRegisteredMember(HttpServletResponse response, String email) throws IOException {
        String signUpToken = JwtTokenProvider.createSignUpToken(email);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.toString(),
                new Errors(Map.of(SIGN_UP_TOKEN, signUpToken)));
        response.setStatus(HttpStatus.NO_CONTENT.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter out = response.getWriter();
        String signUpToken1 = objectMapper.writeValueAsString(errorResponse);
        out.write(signUpToken1);
        out.flush();
        out.close();
    }
}
