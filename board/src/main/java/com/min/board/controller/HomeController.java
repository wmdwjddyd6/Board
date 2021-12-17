package com.min.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String Home(){
        return "index";
    }

    @GetMapping("/admin")
    public String admin() {
        return "/admin";
    }
}
