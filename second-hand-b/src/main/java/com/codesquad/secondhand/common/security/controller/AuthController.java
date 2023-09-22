package com.codesquad.secondhand.common.security.controller;

import com.codesquad.secondhand.common.security.request.RefreshTokenRequest;
import com.codesquad.secondhand.common.security.request.SignUpRequest;
import com.codesquad.secondhand.common.security.response.Tokens;
import com.codesquad.secondhand.common.security.port.AuthUseCase;
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
    public ResponseEntity<Void> signOut(@AuthenticationPrincipal String validatedMemberId) {
        authUseCase.signOut(validatedMemberId);
        return ResponseEntity.noContent().build();
    }
}
