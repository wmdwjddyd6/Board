package com.min.board.controller;

import com.min.board.model.Member;
import com.min.board.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private MemberService memberService;

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
    public String findIdForm(@RequestParam(required = false) String email, Model model) {
        if(email != null) {
            List<Member> memberList = memberService.getMemberByEmail(email);
            model.addAttribute("memberList", memberList);
        }
        return "/account/findIdForm";
    }

    // ID 찾기
    @PostMapping("/findId")
    public String findId(String email, Model model){ // view의 form->input 의 name과 매핑됨.
        if(memberService.checkEmail(email)) {
            String resultUsername = "";

            return "redirect:/findIdForm?email=" + email;
        } else {
            return "redirect:/findIdForm?error=true";
        }
    }


    // PW 찾기
    @GetMapping("passwordReset")
    public String passwordReset(Model model) {
        model.addAttribute("member", new Member());
        return "/account/passwordReset";
    }
}
