package com.starfly.admin.controller;

import com.starfly.admin.security.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping(value = "/login", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        return loginService.login(username, password);
    }

    @GetMapping("/logout")
    public String logout(){
        return "logout";
    }
}
