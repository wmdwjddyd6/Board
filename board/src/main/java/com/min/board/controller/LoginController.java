package com.min.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class LoginController {

    @GetMapping("/loginForm")
    public String loginForm() {
        return "/account/loginForm";
    }

    @PostMapping("/login")
    public void login() {
        int a;
    }

    @GetMapping("/admin")
    public String admin() {
        return "/admin";
    }
}
