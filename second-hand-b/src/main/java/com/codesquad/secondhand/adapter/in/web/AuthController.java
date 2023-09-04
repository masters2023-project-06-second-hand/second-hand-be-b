package com.codesquad.secondhand.adapter.in.web;

import com.codesquad.secondhand.application.port.in.AuthUseCase;
import com.codesquad.secondhand.application.port.in.request.SignUpRequest;
import com.codesquad.secondhand.application.port.in.response.Tokens;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RequiredArgsConstructor
@RequestMapping("/api/members")
@RestController
public class AuthController {

    private final AuthUseCase authUseCase;
    @Value(value = "front.server.address")
    private String frontServerUrl;

    @GetMapping("/signin")
    public RedirectView signIn(@AuthenticationPrincipal OAuth2User oAuth2User, HttpServletResponse response) {
        String email = oAuth2User.getAttribute("email");
        Tokens tokens = authUseCase.signIn(email);
        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + tokens.getAccessToken());
        response.setHeader(HttpHeaders.SET_COOKIE, "refresh_token=" + tokens.getRefreshToken());

        return new RedirectView(frontServerUrl);
    }

    @PostMapping("/signup")
    public ResponseEntity<Tokens> signup(
            @AuthenticationPrincipal String email,
            @RequestBody SignUpRequest signUpRequest
    ) {
        Tokens tokens = authUseCase.signUp(email, signUpRequest);
        return ResponseEntity.ok(tokens);
    }

}
