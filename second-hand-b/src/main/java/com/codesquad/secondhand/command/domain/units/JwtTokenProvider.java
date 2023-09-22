package com.codesquad.secondhand.command.domain.units;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;

public class JwtTokenProvider {

    private static final SecretKey ACCESS_TOKEN_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final SecretKey REFRESH_TOKEN_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final SecretKey SIGN_UP_TOKEN_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final String IS_REGISTERED_CLAIM = "isRegistered";
    private static final String EMAIL_CLAIM = "email";
    private static final String ROLE_CLAIM = "role";
    private static final String SECOND_HAND_CLAIM = "second_hand";
    public static final long THIRTY_MIN = 30 * 60 * 1000L;
    public static final long THIRTY_DAYS = 30 * 60 * 60 * 1000L;
    private static final String TOKEN_DELIMITER = " ";
    private static final int TOKEN_INDEX = 1;

    private JwtTokenProvider() {
        throw new IllegalStateException("Utility class");
    }

    public static String createSignUpToken(String email) {
        Date startDate = new Date();
        return Jwts.builder()
                .claim(EMAIL_CLAIM, email)
                .setIssuer(SECOND_HAND_CLAIM)
                .claim(IS_REGISTERED_CLAIM, false)
                .setIssuedAt(startDate)
                .setExpiration(getSignUpTokenExpiryDate(startDate))
                .signWith(SIGN_UP_TOKEN_KEY)
                .compact();
    }


    public static String createAccessToken(String email, String id, String role, Date startDate) {
        return Jwts.builder()
                .claim(EMAIL_CLAIM, email)
                .claim(IS_REGISTERED_CLAIM, true)
                .claim(ROLE_CLAIM, role)
                .setIssuer(SECOND_HAND_CLAIM)
                .setSubject(id)
                .setIssuedAt(startDate)
                .setExpiration(getAccessTokenExpiryDate(startDate))
                .signWith(ACCESS_TOKEN_KEY)
                .compact();
    }

    public static String createRefreshToken(String email, String id, Date startDate) {
        return Jwts.builder()
                .claim(EMAIL_CLAIM, email)
                .claim(IS_REGISTERED_CLAIM, true)
                .setIssuer(SECOND_HAND_CLAIM)
                .setSubject(id)
                .setIssuedAt(startDate)
                .setExpiration(JwtTokenProvider.getRefreshTokenExpiryDate(startDate))
                .signWith(REFRESH_TOKEN_KEY)
                .compact();
    }

    public static String getIdFormAccessToken(String accessToken) {
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(ACCESS_TOKEN_KEY)
                .build()
                .parseClaimsJws(accessToken);
        return claims.getBody().getSubject();
    }

    public static String getRoleFormAccessToken(String accessToken) {
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(ACCESS_TOKEN_KEY)
                .build()
                .parseClaimsJws(accessToken);
        return claims.getBody().get(ROLE_CLAIM, String.class);
    }

    private static Date getSignUpTokenExpiryDate(Date startDate) {
        return new Date(startDate.getTime() + THIRTY_MIN);
    }

    private static Date getAccessTokenExpiryDate(Date startDate) {
        return new Date(startDate.getTime() + JwtTokenProvider.THIRTY_MIN);
    }

    public static Date getRefreshTokenExpiryDate(Date startDate) {
        return new Date(startDate.getTime() + JwtTokenProvider.THIRTY_DAYS);
    }

    public static String parseTokenFromAuthorization(String header) {
        return header.split(TOKEN_DELIMITER)[TOKEN_INDEX].trim();
    }

    public static String resolveToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null) {
            return null;
        }
        return parseTokenFromAuthorization(header);
    }

    public static boolean isValidAccessToken(String jwtToken, Date now) {
        return isValidToken(jwtToken, now, ACCESS_TOKEN_KEY);
    }

    public static boolean isValidSignUpToken(String jwtToken, Date now) {
        return isValidToken(jwtToken, now, SIGN_UP_TOKEN_KEY);
    }

    private static boolean isValidToken(String jwtToken, Date now, SecretKey secretKey) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(jwtToken);
            return !claims.getBody()
                    .getExpiration()
                    .before(now);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidRefreshToken(String jwtToken, Date now) {
        return isValidToken(jwtToken, now, REFRESH_TOKEN_KEY);
    }

    public static boolean isAccessToken(String jwtToken) {
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(ACCESS_TOKEN_KEY)
                .build()
                .parseClaimsJws(jwtToken);
        return claims.getBody()
                .get(IS_REGISTERED_CLAIM, Boolean.class);
    }

    public static String getEmailFromSignUpToken(String jwtToken) {
        return getEmailFromToken(jwtToken, SIGN_UP_TOKEN_KEY);
    }

    private static String getEmailFromToken(String jwtToken, SecretKey secretKey) {
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwtToken);
        return claims.getBody()
                .get(EMAIL_CLAIM, String.class);
    }

    public static String getEmailFromRefreshToken(String refreshToken) {
        return getEmailFromToken(refreshToken, REFRESH_TOKEN_KEY);
    }

}
