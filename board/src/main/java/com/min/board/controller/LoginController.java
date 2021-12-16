package com.min.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginForm() {
        return "/account/loginForm";
    }

//    @PostMapping
//    public String login() {
//        // 로그인 처리
//    }
}
