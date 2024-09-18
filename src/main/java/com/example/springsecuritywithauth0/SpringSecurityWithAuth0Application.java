package com.example.springsecuritywithauth0;

import com.auth0.*;
import com.auth0.jwk.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;

import java.io.*;

@SpringBootApplication
public class SpringSecurityWithAuth0Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityWithAuth0Application.class, args);
    }

}
