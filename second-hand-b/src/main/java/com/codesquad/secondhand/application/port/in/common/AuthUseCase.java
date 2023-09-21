package com.codesquad.secondhand.application.port.in.common;

import com.codesquad.secondhand.adapter.in.web.common.security.request.SignUpRequest;
import com.codesquad.secondhand.adapter.in.web.common.security.response.Tokens;

public interface AuthUseCase {

    Tokens signIn(String email);

    Tokens signUp(String email, SignUpRequest signUpRequest);

    Tokens getToken(String authentication);

    void signOut(String validatedMemberId);
}
