package com.codesquad.secondhand.common.security.port;

import com.codesquad.secondhand.common.security.request.SignUpRequest;
import com.codesquad.secondhand.common.security.response.Tokens;

public interface AuthUseCase {

    Tokens signIn(String email);

    Tokens signUp(String email, SignUpRequest signUpRequest);

    Tokens getToken(String authentication);

    void signOut(String validatedMemberId);
}
