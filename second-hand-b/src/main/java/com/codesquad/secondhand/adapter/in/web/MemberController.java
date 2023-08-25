package com.codesquad.secondhand.adapter.in.web;

import com.codesquad.secondhand.application.port.in.MemberUseCase;
import com.codesquad.secondhand.application.port.in.request.SignUpRequest;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/members")
@RestController
public class MemberController {

    private final MemberUseCase memberUseCase;

    @GetMapping("/signin")
    public ResponseEntity<Map<String, String>> signIn(@AuthenticationPrincipal OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");
        String accessToken = memberUseCase.signIn(email);
        return ResponseEntity.ok(Map.of("AccessToken", accessToken));
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(
            @AuthenticationPrincipal String email,
            @RequestBody SignUpRequest signUpRequest
    ) {
        String accessToken = memberUseCase.signUp(email, signUpRequest);
        return ResponseEntity.ok(Map.of("AccessToken", accessToken));
    }

}
