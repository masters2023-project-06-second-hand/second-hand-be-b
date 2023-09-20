package com.codesquad.secondhand.adapter.in.web.config.jwt;

import com.codesquad.secondhand.adapter.in.web.security.JwtAccessToken;
import com.codesquad.secondhand.application.port.in.MemberUseCase;
import com.codesquad.secondhand.domain.member.Member;
import com.codesquad.secondhand.domain.units.JwtTokenProvider;
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

    private final MemberUseCase memberUseCase;

    public JwtSignInAuthenticationFilter(MemberUseCase memberUseCase) {
        this.memberUseCase = memberUseCase;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        Authentication existsAuthentication = SecurityContextHolder.getContext().getAuthentication();
        if (existsAuthentication != null && existsAuthentication.isAuthenticated()) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = JwtTokenProvider.resolveToken(request);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        Date now = new Date();
        if (JwtTokenProvider.isValidAccessToken(token, now) && JwtTokenProvider.isAccessToken(token)) {
            String email = JwtTokenProvider.getEmailFromAccessToken(token);
            Member member = memberUseCase.getByEmail(email);
            Authentication authentication = new JwtAccessToken(member, member.getRoleAuthority());
            authentication.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
