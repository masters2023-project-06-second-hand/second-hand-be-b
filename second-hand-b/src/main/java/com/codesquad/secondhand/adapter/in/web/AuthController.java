package com.codesquad.secondhand.adapter.in.web;

import com.codesquad.secondhand.application.port.in.AuthUseCase;
import com.codesquad.secondhand.application.port.in.request.SignUpRequest;
import com.codesquad.secondhand.application.port.in.response.Tokens;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
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

    @GetMapping("/signin")
    public ResponseEntity<Tokens> signIn(@AuthenticationPrincipal OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");
        Tokens tokens = authUseCase.signIn(email);
        return ResponseEntity.ok(tokens);
    }

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
