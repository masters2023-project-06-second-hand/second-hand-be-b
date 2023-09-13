package com.codesquad.secondhand.adapter.in.web.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.codesquad.secondhand.application.port.in.AuthUseCase;
import com.codesquad.secondhand.application.service.in.exception.ErrorResponse;
import com.codesquad.secondhand.application.service.in.exception.Errors;
import com.codesquad.secondhand.adapter.in.web.response.Tokens;
import com.codesquad.secondhand.domain.units.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

class AuthenticationResponseHandlerImplTest {


    public static final String TEST_EMAIL = "test@example.com";
    public static final String MOCK_ACCESS_TOKEN = "mockAccessToken";
    public static final String MOCK_REFRESH_TOKEN = "mockRefreshToken";
    private AuthenticationResponseHandlerImpl handler;
    private AuthUseCase authUseCase;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        authUseCase = mock(AuthUseCase.class);
        objectMapper = new ObjectMapper();
        handler = new AuthenticationResponseHandlerImpl(authUseCase, objectMapper);
    }

    @DisplayName("이메일 정보로 Tokens를 만들고 Response 담아 return 한다")
    @Test
    void testHandleSuccessfulAuthentication() throws IOException {
        Tokens mockTokens = new Tokens(MOCK_ACCESS_TOKEN, MOCK_REFRESH_TOKEN, 1L);

        when(authUseCase.signIn(TEST_EMAIL)).thenReturn(mockTokens);

        MockHttpServletResponse response = new MockHttpServletResponse();

        handler.handleSuccessfulAuthentication(response, TEST_EMAIL);

        Assertions.assertAll(
                () -> assertThat(response.getContentType())
                        .isEqualTo(MediaType.APPLICATION_JSON_VALUE),
                () -> assertThat(response.getContentAsString())
                        .isEqualTo(objectMapper.writeValueAsString(mockTokens))
        );
    }

    @DisplayName("이메일 정보로 SingUpToken을 만들고 Response에 담는다")
    @Test
    void testHandleNotRegisteredMember() throws IOException {
        String signUpToken = JwtTokenProvider.createSignUpToken(TEST_EMAIL);

        MockHttpServletResponse response = new MockHttpServletResponse();

        handler.handleNotRegisteredMember(response, TEST_EMAIL);

        ErrorResponse expectedResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.toString(),
                new Errors(Map.of(AuthenticationResponseHandlerImpl.SIGN_UP_TOKEN, signUpToken)));

        Assertions.assertAll(
                () -> assertThat(response.getContentType())
                        .isEqualTo(MediaType.APPLICATION_JSON_VALUE),
                () -> assertThat(response.getContentAsString())
                        .isEqualTo(objectMapper.writeValueAsString(expectedResponse))
        );
    }
}
