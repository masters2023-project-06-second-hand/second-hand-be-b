package com.codesquad.secondhand.adapter.in.web.config.jwt;

import com.codesquad.secondhand.adapter.in.web.security.JwtSignUpToken;
import com.codesquad.secondhand.domain.member.Role;
import com.codesquad.secondhand.domain.units.JwtTokenProvider;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtSignUpAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtSignUpAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        Authentication existsAuthentication = SecurityContextHolder.getContext().getAuthentication();
        if (existsAuthentication != null && existsAuthentication.isAuthenticated()) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = jwtTokenProvider.resolveToken(request);
        Date now = new Date();
        if (token != null && jwtTokenProvider.validateToken(token, now)
                && !jwtTokenProvider.isAccessToken(token)) {
            String email = jwtTokenProvider.getEmail(token);
            Authentication authentication = new JwtSignUpToken(email, Collections.singleton(new SimpleGrantedAuthority(
                    Role.USER.getKey())));
            authentication.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
