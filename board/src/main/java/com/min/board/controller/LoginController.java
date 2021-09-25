package com.min.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @GetMapping("/join") // "/join"
    public String joinForm(){ // 정적
        return "join";  // join.html
    }
    @PostMapping("/member/join")
    public String join(@RequestBody ){
        return
    }
}
