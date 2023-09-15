package com.codesquad.secondhand.application.port.in;

import com.codesquad.secondhand.adapter.in.web.request.SignUpRequest;
import com.codesquad.secondhand.adapter.in.web.response.Tokens;
import com.codesquad.secondhand.domain.member.Member;

public interface AuthUseCase {

    Tokens signIn(String email);

    Tokens signUp(String email, SignUpRequest signUpRequest);

    Tokens getToken(String authentication);

    void signOut(Member member);
}
