package com.codesquad.secondhand.adapter.in.web;

import static com.codesquad.secondhand.adapter.in.web.MemberSteps.토큰_검증;
import static com.codesquad.secondhand.adapter.in.web.MemberSteps.회원_가입_한다;

import com.codesquad.secondhand.domain.units.JwtTokenProvider;
import com.codesquad.secondhand.utils.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class AuthAcceptanceTest extends AcceptanceTest {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @DisplayName("signUpToken와 함께 회원가입을 요청하면 Tokens를 보낸다")
    @Test
    void shouldReturnTokensWhenSignUpWithSignUpToken() {
        // signUpToken을 만든다
        String signUpToken = jwtTokenProvider.createSignUpToken(TEST_EMAIL);

        // when
        var 회원_토큰 = 회원_가입_한다(signUpToken);

        // then
        토큰_검증(회원_토큰);
    }

}
