package com.codesquad.secondhand.adapter.in.web.config;

import static org.springframework.security.config.Customizer.withDefaults;

import com.codesquad.secondhand.adapter.in.web.config.jwt.JwtSignInAuthenticationFilter;
import com.codesquad.secondhand.adapter.in.web.config.jwt.JwtSignUpAuthenticationFilter;
import com.codesquad.secondhand.application.port.out.MemberRepository;
import com.codesquad.secondhand.domain.member.Role;
import com.codesquad.secondhand.domain.units.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class OAuth2LoginSecurityConfig {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    MemberRepository memberRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(
                        requestMatcherRegistry -> requestMatcherRegistry
                                .mvcMatchers(HttpMethod.GET, "/api/members/signin")
                                .authenticated()
                                .mvcMatchers(HttpMethod.POST, "/api/members/signup")
                                .hasAuthority(Role.USER.getKey())
                                .anyRequest()
                                .hasAnyAuthority(Role.MANAGER.getKey(), Role.MEMBER.getKey())
                )
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(
                        new JwtSignUpAuthenticationFilter(jwtTokenProvider),
                        OAuth2AuthorizationRequestRedirectFilter.class)
                .addFilterBefore(
                        new JwtSignInAuthenticationFilter(jwtTokenProvider, memberRepository),
                        JwtSignUpAuthenticationFilter.class)
                .oauth2Login(withDefaults())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .build();
    }

    @Bean
    public DefaultOAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository) {

        var authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
                .authorizationCode()
                .clientCredentials()
                .refreshToken()
                .build();

        var authorizedClientManager = new DefaultOAuth2AuthorizedClientManager(
                clientRegistrationRepository,
                authorizedClientRepository
        );
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        return authorizedClientManager;
    }
}
