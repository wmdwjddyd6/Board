package com.min.board.controller;

import com.min.board.model.Member;
import com.min.board.model.MemberForm;
import com.min.board.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class JoinController {

    private MemberService memberService;

    public JoinController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/member/join")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return "join";
    }

    //password null로 찍힘
    @PostMapping("/member/join")
    public String createMember(@ModelAttribute MemberForm memberForm){
        Member member = new Member();

        member.setUsername(memberForm.getUsername());
        member.setPassword(memberForm.getPassword());
        member.setRole(memberForm.getRole());

        memberService.join(member);

        return "redirect:/";
    }

}
