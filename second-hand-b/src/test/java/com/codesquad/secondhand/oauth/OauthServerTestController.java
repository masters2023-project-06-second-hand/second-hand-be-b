package com.codesquad.secondhand.oauth;

import com.codesquad.secondhand.domain.units.JwtTokenProvider;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping
public class OauthServerTestController {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @GetMapping("/fake-oauth2/authorize")
    public RedirectView authorize(
            @RequestParam(name = "response_type") String responseType,
            @RequestParam(name = "client_id") String clientId,
            @RequestParam(name = "scope") String scope,
            @RequestParam(name = "state") String state,
            @RequestParam(name = "redirect_uri") String redirectUri,
            HttpSession session
    ) {

        return new RedirectView(redirectUri + "?code=" + "1" + "&state=" + state);
    }


    @PostMapping("/fake-oauth2/token")
    public ResponseEntity<Map<String, String>> user() {
        String signUpToken = jwtTokenProvider.createSignUpToken("test@email.com");
        return ResponseEntity.ok(Map.of(
                        "access_token", signUpToken,
                        "token_type", "bearer",
                        "expires_in", "3600",
                        "scope", "email"
                )
        );
    }

    @GetMapping("/fake-oauth2/userinfo")
    public ResponseEntity<Map<String, String>> userinfo() {
        return ResponseEntity.ok(Map.of(
                        "sub", "1234567890",
                        "name", "John Doe",
                        "user_name", "joj",
                        "given_name", "John",
                        "family_name", "Doe",
                        "email", "john.doe@example.com",
                        "locale", "en-US"
                )
        );
    }
}
