package com.codesquad.secondhand.common.security.config;

import com.codesquad.secondhand.command.domain.member.Role;
import com.codesquad.secondhand.common.security.filter.JwtSignInAuthenticationFilter;
import com.codesquad.secondhand.common.security.filter.JwtSignUpAuthenticationFilter;
import com.codesquad.secondhand.common.security.handler.OAuth2LoginSuccessHandler;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${cors.allowed.origins}")
    public List<String> allowedOrigins;
    @Value("${cors.allowed.methods}")
    public List<String> allowedMethods;
    @Value("${cors.allowed.headers}")
    public List<String> allowedHeaders;
    @Value("${cors.mapping.pattern}")
    public String corsMappingPattern;
    @Value("${anonymous.allowed.get.urls}")
    private String[] anonymousGetAllowedUrls;
    @Value("${anonymous.allowed.post.urls}")
    private String[] anonymousPostAllowedUrls;
    @Value("${oauth2user.allowed.urls}")
    private String[] userAllowedUrls;
    @Value("${member.allowed.urls}")
    private String[] memberAllowedUrls;

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity httpSecurity,
            OAuth2LoginSuccessHandler successHandler
    ) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(
                        requestMatcherRegistry -> requestMatcherRegistry
                                .mvcMatchers(HttpMethod.GET, anonymousGetAllowedUrls)
                                .permitAll()
                                .mvcMatchers(HttpMethod.POST, anonymousPostAllowedUrls)
                                .permitAll()
                                .mvcMatchers(HttpMethod.POST, userAllowedUrls)
                                .hasAuthority(Role.USER.getKey())
                                .mvcMatchers(HttpMethod.GET, memberAllowedUrls)
                                .hasAnyAuthority(Role.MANAGER.getKey(), Role.MEMBER.getKey())
                                .anyRequest()
                                .hasAnyAuthority(Role.MANAGER.getKey(), Role.MEMBER.getKey())
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                .addFilterBefore(
                        new JwtSignUpAuthenticationFilter(),
                        OAuth2AuthorizationRequestRedirectFilter.class)
                .addFilterBefore(
                        new JwtSignInAuthenticationFilter(),
                        JwtSignUpAuthenticationFilter.class)
                .oauth2Login(configurer -> configurer.successHandler(successHandler))
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
        configuration.setAllowedOrigins(allowedOrigins);
        configuration.setAllowedMethods(allowedMethods);
        configuration.setAllowedHeaders(allowedHeaders);
        configuration.setAllowCredentials(true);
        var urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration(corsMappingPattern, configuration);
        return urlBasedCorsConfigurationSource;
    }
}
