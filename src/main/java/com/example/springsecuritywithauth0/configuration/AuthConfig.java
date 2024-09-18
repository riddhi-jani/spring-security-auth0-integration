package com.example.springsecuritywithauth0.configuration;

import com.auth0.*;
import com.auth0.jwk.*;
import com.example.springsecuritywithauth0.controller.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.logout.*;

import javax.servlet.http.*;
import java.io.*;

@Configuration
@EnableWebSecurity
public class AuthConfig {
    @Value(value = "${com.auth0.domain}")
    private String domain;
    @Value(value = "${com.auth0.clientId}")
    private String clientId;
    @Value(value = "${com.auth0.clientSecret}")
    private String clientSecret;
    @Value(value = "${com.auth0.managementApi.clientId}")
    private String managementApiClientId;
    @Value(value = "${com.auth0.managementApi.clientSecret}")
    private String managementApiClientSecret;
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new LogoutController();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/callback", "/login", "/").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler()).permitAll();
        return http.build();
    }
    public String getContextPath(HttpServletRequest request) {
        String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        return path;
    }
    public String getDomain() {
        return domain;
    }
    public String getClientId() {
        return clientId;
    }
    public String getClientSecret() {
        return clientSecret;
    }
    public String getManagementApiClientId() {
        return managementApiClientId;
    }
    public String getManagementApiClientSecret() {
        return managementApiClientSecret;
    }
    @Bean
    public AuthenticationController authenticationController() throws UnsupportedEncodingException {
        JwkProvider jwkProvider = new JwkProviderBuilder(domain).build();
        return AuthenticationController.newBuilder(domain, clientId, clientSecret)
                .withJwkProvider(jwkProvider)
                .build();
    }
}