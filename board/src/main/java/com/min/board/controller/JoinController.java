package com.min.board.controller;

import com.min.board.model.Member;
import com.min.board.service.MemberService;
import com.min.board.validator.MemeberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/account")
public class JoinController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemeberValidator memeberValidator;

    // 회원가입 폼
    @GetMapping("/joinForm")
    public String joinForm(Model model) {
        model.addAttribute("member", new Member());
        return "/account/joinForm";
    }

    // 회원가입
    @PostMapping("/join")
    public String join(@Valid Member member, BindingResult bindingResult){ // view의 form->input 의 name과 매핑됨.
        memeberValidator.validate(member, bindingResult);

        if(bindingResult.hasErrors()) {
            return "/account/joinForm";
        }

        String encPwd = memberService.pwdEncoding(member.getPassword());
        member.setPassword(encPwd);

        memberService.join(member);

        return "redirect:/loginForm";
    }
}
