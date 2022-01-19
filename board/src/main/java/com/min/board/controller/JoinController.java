package com.min.board.controller;

import com.min.board.model.Member;
import com.min.board.service.MemberService;
import com.min.board.validator.MemberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class JoinController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberValidator memberValidator;

    // 회원가입 폼
    @GetMapping("/joinForm")
    public String joinForm(Model model) {
        model.addAttribute("member", new Member());
        return "register/joinForm";
    }

    // 회원가입
    @PostMapping("/join")
    public String join(@Valid Member member, BindingResult bindingResult){ // view의 form->input 의 name과 매핑됨.
        memberValidator.validate(member, bindingResult);

        if(bindingResult.hasErrors()) {
            return "register/joinForm";
        }

        int result = memberService.join(member, "ROLE_USER");

        if(result > 0) {
            return "redirect:/loginForm";
        } else {
            return "register/joinForm";
        }
    }
}
