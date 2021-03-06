package com.min.board.controller;

import com.min.board.model.MemberDto;
import com.min.board.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/*
*
* 개인 메뉴 항목 관련 컨트롤러
*
* */
@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private MemberService memberService;

    // 개인 메뉴 탭으로 이동
    @GetMapping("/userMenu")
    public String userMenu(Model model, Principal principal) {
        MemberDto memberDto = memberService.getMember(principal.getName());
        String userEmail = memberDto.getEmail();
        model.addAttribute("userEmail", userEmail);

        return "account/userMenu";
    }

    // 회원탈퇴 화면으로 이동
    @GetMapping("/secessionForm")
    public String secessionForm() {
        return "account/secessionForm";
    }

    // 회원탈퇴 진행
    @PostMapping("/secession")
    public String secession(Principal principal, String password) throws Exception {
        String loginUsername = principal.getName();

        if(memberService.checkPassword(loginUsername, password)) { // 패스워드, 체크박스 확인
           memberService.secession(loginUsername); // 회원탈퇴
           SecurityContextHolder.clearContext(); // 로그아웃
           return "redirect:/";
        } else {
           return "redirect:/account/secessionForm?error=true";
        }
    }

    // 비밀번호 변경 화면으로 이동
    @GetMapping("/changePasswordForm")
    public String changePasswordForm() {
        return "account/changePasswordForm";
    }

    // 비밀번호 변경
    @PostMapping("/changePassword")
    public String changePassword(String oldPassword, String newPassword, Principal principal) throws Exception {
        String loginUsername = principal.getName();

        if(oldPassword.length() < 6 || newPassword.length() < 6) { // 비밀번호 6자리 이상의 규칙을 지켰는지 확인
            return "redirect:/account/changePasswordForm?error=true";
        } else if(memberService.checkPassword(loginUsername, oldPassword)) { // 이전 비밀번호 체크
            memberService.changePassword(loginUsername, newPassword); // 비밀번호 변경
            SecurityContextHolder.clearContext(); // 로그아웃
            return "redirect:/";
        } else {
            return "redirect:/account/changePasswordForm?error=true";
        }
    }
}
