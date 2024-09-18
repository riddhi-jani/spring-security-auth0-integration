package com.example.springsecuritywithauth0.controller;

import com.example.springsecuritywithauth0.entity.*;
import org.json.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.*;

import javax.servlet.http.*;

@Controller
public class UserController {
    @Autowired
    AuthController authController;
    @GetMapping(value="/get-users")
    @ResponseBody
    public ResponseEntity<String> users(HttpServletRequest request, HttpServletResponse response) {

        HttpEntity<String> entity = new HttpEntity<String>(setHeader());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate
                .exchange("https://dev-zr9pcrck.us.auth0.com/api/v2/users", HttpMethod.GET, entity, String.class);
        return result;
    }
    @GetMapping(value = "/create-user")
    @ResponseBody
    public ResponseEntity<String> createUser(HttpServletResponse response) {
        User user = new User();
        user.setEmail("norman.lewis@email.com");
        user.setPassword("Pa33w0rd");
        user.setConnection("Username-Password-Authentication");
        HttpEntity<User> request = new HttpEntity<>(user,setHeader());
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> result = restTemplate
                    .postForEntity("https://dev-zr9pcrck.us.auth0.com/api/v2/users", request, String.class);
            return result;
        }
        catch(Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
        }
    }
    public HttpHeaders setHeader(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + authController.getManagementApiToken());
        return headers;
    }
}
