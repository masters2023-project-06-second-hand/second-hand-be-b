package com.codesquad.secondhand.adapter.in.web.config;

import com.codesquad.secondhand.adapter.in.web.config.jwt.JwtSignInAuthenticationFilter;
import com.codesquad.secondhand.adapter.in.web.config.jwt.JwtSignUpAuthenticationFilter;
import com.codesquad.secondhand.application.port.out.MemberRepository;
import com.codesquad.secondhand.domain.member.Role;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class OAuth2LoginSecurityConfig {

    @Autowired
    private OAuth2LoginSuccessHandler successHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity,
            MemberRepository memberRepository) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(
                        requestMatcherRegistry -> requestMatcherRegistry
                                .mvcMatchers(HttpMethod.GET, "/api/members/accesstoken", "/login/oauth2/code/google")
                                .permitAll()
                                .mvcMatchers(HttpMethod.GET, "/api/members/signin")
                                .authenticated()
                                .mvcMatchers(HttpMethod.POST, "/api/members/signup")
                                .hasAuthority(Role.USER.getKey())
                                .anyRequest()
                                .hasAnyAuthority(Role.MANAGER.getKey(), Role.MEMBER.getKey())
                )
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(
                        new JwtSignUpAuthenticationFilter(),
                        OAuth2AuthorizationRequestRedirectFilter.class)
                .addFilterBefore(
                        new JwtSignInAuthenticationFilter(memberRepository),
                        JwtSignUpAuthenticationFilter.class)
                .oauth2Login(configurer -> configurer.defaultSuccessUrl("/api/members/signin")
                        .successHandler(successHandler))
                .cors().configurationSource(corsConfigurationSource())
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


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
