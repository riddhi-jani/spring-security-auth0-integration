package com.example.springsecuritywithauth0.controller;

import com.auth0.*;
import com.auth0.jwt.*;
import com.auth0.jwt.interfaces.*;
import com.example.springsecuritywithauth0.configuration.*;
import org.json.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.*;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;

@Controller
public class AuthController {
    @Autowired
    private AuthConfig config;
    @Autowired
    private AuthenticationController authenticationController;
    @GetMapping(value = "/login")
    protected void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String redirectUri = "http://localhost:8080/callback";
        String authorizeUrl = authenticationController.buildAuthorizeUrl(request,response,redirectUri)
                .withScope("openid email")
                .build();
        response.sendRedirect(authorizeUrl);
    }
    @GetMapping(value="/callback")
    public void callback(HttpServletRequest request, HttpServletResponse response) throws IOException, IdentityVerificationException {
        Tokens tokens = authenticationController.handle(request, response);

        DecodedJWT jwt = JWT.decode(tokens.getIdToken());
        TestingAuthenticationToken authToken2 = new TestingAuthenticationToken(jwt.getSubject(),
                jwt.getToken());
        authToken2.setAuthenticated(true);

        SecurityContextHolder.getContext().setAuthentication(authToken2);
        response.sendRedirect(config.getContextPath(request) + "/home");
    }
    public String getManagementApiToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject requestBody = new JSONObject();
        requestBody.put("client_id", config.getManagementApiClientId());
        requestBody.put("client_secret", config.getManagementApiClientSecret());
        requestBody.put("audience", "https://dev-zr9pcrck.us.auth0.com/api/v2/");
        requestBody.put("grant_type", "client_credentials");
        HttpEntity<String> request = new HttpEntity<String>(requestBody.toString(), headers);
        RestTemplate restTemplate = new RestTemplate();
        HashMap<String, String> result = restTemplate.postForObject("https://dev-zr9pcrck.us.auth0.com/oauth/token", request, HashMap.class);

        return result.get("access_token");
    }
}
