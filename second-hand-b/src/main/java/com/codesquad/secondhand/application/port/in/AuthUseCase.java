package com.codesquad.secondhand.application.port.in;

import com.codesquad.secondhand.adapter.in.web.security.request.SignUpRequest;
import com.codesquad.secondhand.adapter.in.web.security.response.Tokens;

public interface AuthUseCase {

    Tokens signIn(String email);

    Tokens signUp(String email, SignUpRequest signUpRequest);

    Tokens getToken(String authentication);

    void signOut(String validatedMemberId);
}
