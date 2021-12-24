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
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/account")
public class JoinController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberValidator memberValidator;

    // 회원가입 폼
    @GetMapping("/joinForm")
    public String joinForm(Model model) {
        model.addAttribute("member", new Member());
        return "/account/joinForm";
    }

    // 회원가입
    @PostMapping("/join")
    public String join(@Valid Member member, BindingResult bindingResult){ // view의 form->input 의 name과 매핑됨.
        memberValidator.validate(member, bindingResult);

        if(bindingResult.hasErrors()) {
            return "/account/joinForm";
        }

        memberService.join(member);

        return "redirect:/loginForm";
    }
}
