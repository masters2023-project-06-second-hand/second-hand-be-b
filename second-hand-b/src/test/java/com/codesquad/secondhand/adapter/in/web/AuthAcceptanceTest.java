package com.codesquad.secondhand.adapter.in.web;

import static com.codesquad.secondhand.adapter.in.web.AuthSteps.토큰_검증;
import static com.codesquad.secondhand.adapter.in.web.AuthSteps.회원_가입_한다;
import static com.codesquad.secondhand.utils.RestDocsUtils.출력_필드_추가;

import com.codesquad.secondhand.domain.units.JwtTokenProvider;
import com.codesquad.secondhand.utils.AcceptanceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AuthAcceptanceTest extends AcceptanceTest {


    @DisplayName("signUpToken와 함께 회원가입을 요청하면 Tokens를 보낸다")
    @Test
    void shouldReturnTokensWhenSignUpWithSignUpToken() {
        출력_필드_추가("auth_signup", spec);

        // signUpToken을 만든다
        String signUpToken = JwtTokenProvider.createSignUpToken(AYAAN_EMAIL);

        // when
        var 회원_토큰 = 회원_가입_한다(signUpToken, spec);

        // then
        토큰_검증(회원_토큰);
    }

}
