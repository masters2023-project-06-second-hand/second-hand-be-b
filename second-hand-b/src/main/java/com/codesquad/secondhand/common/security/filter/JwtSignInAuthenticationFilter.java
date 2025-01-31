package com.codesquad.secondhand.common.security.filter;

import com.codesquad.secondhand.command.domain.units.JwtTokenProvider;
import com.codesquad.secondhand.common.security.token.JwtAccessToken;
import java.io.IOException;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtSignInAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String token = JwtTokenProvider.resolveToken(request);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        Date now = new Date();
        if (JwtTokenProvider.isValidAccessToken(token, now) && JwtTokenProvider.isAccessToken(token)) {
            String id = JwtTokenProvider.getIdFormAccessToken(token);
            String role = JwtTokenProvider.getRoleFormAccessToken(token);
            Authentication authentication = new JwtAccessToken(id, role);
            authentication.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
