package com.example.springsecuritywithauth0.controller;

import com.example.springsecuritywithauth0.configuration.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.*;
import org.springframework.security.web.authentication.logout.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.io.*;

@Controller
public class LogoutController implements LogoutSuccessHandler {
    @Autowired
    private AuthConfig config;
    @Override
    public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse res,
                                Authentication authentication) throws IOException {
        if (req.getSession() != null) {
            req.getSession().invalidate();
        }
        String returnTo = config.getContextPath(req);
        String logoutUrl = String.format(
                "https://%s/v2/logout?client_id=%s&returnTo=%s",
                config.getDomain(),
                config.getClientId(),
                returnTo);
        res.sendRedirect(logoutUrl);
    }
}