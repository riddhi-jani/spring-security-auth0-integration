package com.example.springsecuritywithauth0.controller;

import com.auth0.jwt.*;
import com.auth0.jwt.interfaces.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    @GetMapping(value = "/home")
    @ResponseBody
    public String home(final Authentication authentication) {
        TestingAuthenticationToken token = (TestingAuthenticationToken) authentication;
        DecodedJWT jwt = JWT.decode(token.getCredentials().toString());
        String email = jwt.getClaims().get("email").asString();
        return "Welcome, " + email + "!";
    }
    @GetMapping(value = "/")
    @ResponseBody
    public String logout() {

        return "Hello";
    }
}