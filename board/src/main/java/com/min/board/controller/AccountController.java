package com.min.board.controller;

import com.min.board.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private MemberService memberService;

    // 유저 메뉴 탭으로 이동
    @GetMapping("/userMenu")
    public String userMenu(Model model, Principal principal) {
        model.addAttribute("userEmail", memberService.getUserEmail(principal.getName()));

        return "/account/userMenu";
    }

    // 회원탈퇴 화면으로 이동
    @GetMapping("/secessionForm")
    public String secessionForm() {
        return "/account/secessionForm";
    }

    // 회원탈퇴 진행
    @PostMapping("/secession")
    public String secession(Principal principal, String password, @RequestBody(required = false) String check) {
        String username = principal.getName();

        if(memberService.checkPassword(username, password) && !check.isEmpty()) {
           memberService.secession(username);
           SecurityContextHolder.clearContext(); // 회원탈퇴 시 로그아웃

           return "redirect:/";
        } else {
           return "redirect:/account/secessionForm?error=true";
        }
    }
}
