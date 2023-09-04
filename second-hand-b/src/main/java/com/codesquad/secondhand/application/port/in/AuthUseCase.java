package com.codesquad.secondhand.application.port.in;

import com.codesquad.secondhand.application.port.in.request.SignUpRequest;
import com.codesquad.secondhand.application.port.in.response.Tokens;

public interface AuthUseCase {

    Tokens signIn(String email);

    Tokens signUp(String email, SignUpRequest signUpRequest);

    Tokens getAccessToken(String authentication);
}
