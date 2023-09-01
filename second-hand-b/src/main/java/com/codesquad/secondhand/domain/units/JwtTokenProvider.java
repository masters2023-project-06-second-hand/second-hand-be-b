package com.codesquad.secondhand.domain.units;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private static final SecretKey KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final String IS_REGISTERED_CLAIM = "isRegistered";
    private static final String EMAIL_CLAIM = "email";
    private static final String SECOND_HAND_CLAIM = "second_hand";
    private static final long THIRTY_MIN = 30 * 60 * 1000L;
    private static final String TOKEN_DELIMITER = " ";
    private static final int TOKEN_INDEX = 1;

    public String createAccessToken(String email, String id) {
        Date startDate = new Date();
        return Jwts.builder()
                .claim(EMAIL_CLAIM, email)
                .claim(IS_REGISTERED_CLAIM, true)
                .setIssuer(SECOND_HAND_CLAIM)
                .setSubject(id)
                .setIssuedAt(startDate)
                .setExpiration(new Date(startDate.getTime() + THIRTY_MIN))
                .signWith(KEY)
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null) {
            return null;
        }
        return header.split(TOKEN_DELIMITER)[TOKEN_INDEX].trim();
    }

    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(jwtToken);
            return !claims.getBody()
                    .getExpiration()
                    .before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String createSignUpToken(String email) {
        Date startDate = new Date();
        return Jwts.builder()
                .claim(EMAIL_CLAIM, email)
                .setIssuer(SECOND_HAND_CLAIM)
                .claim(IS_REGISTERED_CLAIM, false)
                .setIssuedAt(startDate)
                .setExpiration(new Date(startDate.getTime() + THIRTY_MIN))
                .signWith(KEY)
                .compact();
    }

    public boolean isAccessToken(String jwtToken) {
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(jwtToken);
        return claims.getBody()
                .get(IS_REGISTERED_CLAIM, Boolean.class);
    }

    public String getEmail(String jwtToken) {
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(jwtToken);
        return claims.getBody()
                .get(EMAIL_CLAIM, String.class);
    }
}
