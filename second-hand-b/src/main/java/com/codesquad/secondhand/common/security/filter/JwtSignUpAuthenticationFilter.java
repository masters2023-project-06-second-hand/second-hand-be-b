package com.codesquad.secondhand.common.security.filter;

import com.codesquad.secondhand.command.domain.member.Role;
import com.codesquad.secondhand.command.domain.units.JwtTokenProvider;
import com.codesquad.secondhand.common.security.token.JwtSignUpToken;
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


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String token = JwtTokenProvider.resolveToken(request);
        Date now = new Date();
        if (token != null && JwtTokenProvider.isValidSignUpToken(token, now)) {
            String email = JwtTokenProvider.getEmailFromSignUpToken(token);
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
