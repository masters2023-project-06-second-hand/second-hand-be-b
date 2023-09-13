package com.codesquad.secondhand.adapter.in.web;

import com.codesquad.secondhand.application.port.in.AuthUseCase;
import com.codesquad.secondhand.adapter.in.web.request.SignUpRequest;
import com.codesquad.secondhand.adapter.in.web.response.Tokens;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/members")
@RestController
public class AuthController {

    private final AuthUseCase authUseCase;

    @PostMapping("/signup")
    public ResponseEntity<Tokens> signup(
            @AuthenticationPrincipal String email,
            @RequestBody SignUpRequest signUpRequest
    ) {
        Tokens tokens = authUseCase.signUp(email, signUpRequest);
        return ResponseEntity.ok(tokens);
    }

    @GetMapping("/accesstoken")
    public ResponseEntity<Tokens> getAccessToken(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authentication
    ) {
        Tokens tokens = authUseCase.getAccessToken(authentication);
        return ResponseEntity.ok(tokens);
    }

}
