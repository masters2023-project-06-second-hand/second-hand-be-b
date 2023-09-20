package com.codesquad.secondhand.adapter.in.web.controller;

import com.codesquad.secondhand.adapter.in.web.request.security.RefreshTokenRequest;
import com.codesquad.secondhand.adapter.in.web.request.security.SignUpRequest;
import com.codesquad.secondhand.adapter.in.web.response.security.Tokens;
import com.codesquad.secondhand.application.port.in.AuthUseCase;
import com.codesquad.secondhand.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping
@RestController
public class AuthController {

    private final AuthUseCase authUseCase;

    @PostMapping("/api/members/signup")
    public ResponseEntity<Tokens> signup(
            @AuthenticationPrincipal String email,
            @RequestBody SignUpRequest signUpRequest
    ) {
        Tokens tokens = authUseCase.signUp(email, signUpRequest);
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/api/oauth2/token")
    public ResponseEntity<Tokens> getAccessToken(
            @RequestBody RefreshTokenRequest refreshTokenRequest
    ) {
        Tokens tokens = authUseCase.getToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/api/members/signout")
    public ResponseEntity<Void> signOut(@AuthenticationPrincipal Member member) {
        authUseCase.signOut(member);
        return ResponseEntity.noContent().build();
    }
}
