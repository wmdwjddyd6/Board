package com.min.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // 게시판 메인 홈으로
    @GetMapping("/")
    public String Home(){
        return "index";
    }

    // 관리자 홈으로
    @GetMapping("/admin")
    public String admin() {
        return "/admin";
    }
}
