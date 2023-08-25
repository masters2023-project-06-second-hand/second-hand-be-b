package com.codesquad.secondhand.application.port.in;

import com.codesquad.secondhand.application.port.in.request.SignUpRequest;

public interface MemberUseCase {

    String signIn(String email);

    String signUp(String email, SignUpRequest signUpRequest);
}
