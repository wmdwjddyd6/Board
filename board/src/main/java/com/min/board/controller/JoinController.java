package com.min.board.controller;

import com.min.board.model.Member;
import com.min.board.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class JoinController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/joinForm")
    public String joinForm() {
        return "/account/joinForm";
    }

    @PostMapping("/join")
    public String join(Member member){ // view의 form->input 의 name과 매핑됨.
        String encPwd = bCryptPasswordEncoder.encode(member.getPassword());
        member.setPassword(encPwd);

        memberService.join(member);

        return "redirect:/loginForm";
    }
}
