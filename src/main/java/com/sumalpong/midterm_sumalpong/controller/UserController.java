package com.sumalpong.midterm_sumalpong.controller;

import java.util.Map;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    public UserController() {
    }

    @GetMapping
    public String index() {
        return "<h1>Hello, this is the landing page.</h1>";
    }

    @GetMapping({"/user-info"})
    public Map<String, Object> getUser(@AuthenticationPrincipal OAuth2User oAuth2User) {
        return oAuth2User.getAttributes();
    }

    @GetMapping({"/secured"})
    public String secured() {
        return "<h1>Hello, this is the secured page.</h1>";
    }
}
