package com.min.board.controller;

import com.min.board.model.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    // 로그인 폼
    @GetMapping("/loginForm")
    public String loginForm(@RequestParam(value = "error", required = false) String error,
                            Model model) {
        model.addAttribute("error", error);
        return "/account/loginForm";
    }

    // 로그인 실패
    @PostMapping("/fail_login")
    public String handleFailedLogin() {
        return "redirect:/loginForm?error=true";
    }

    // ID 찾기
    @GetMapping("findIdForm")
    public String findIdForm(Model model) {
        model.addAttribute("member", new Member());
        return "/account/findIdForm";
    }

    // PW 찾기
    @GetMapping("passwordReset")
    public String passwordReset(Model model) {
        model.addAttribute("member", new Member());
        return "/account/passwordReset";
    }
}
