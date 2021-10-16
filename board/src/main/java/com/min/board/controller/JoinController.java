package com.min.board.controller;

import com.min.board.domain.Member;
import com.min.board.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class JoinController {

    private MemberService memberService;

    public JoinController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/member/join")
    public String createForm(){
        return "join";
    }

    @PostMapping(value = "/member/join")
    public String createMember(MemberForm memberForm){
        Member member = new Member();

        member.setUsername(memberForm.getUsername());
        member.setUserId(memberForm.getUserId());
        member.setPassword(memberForm.getPassword());

        memberService.join(member);

        System.out.println(member.getUserId());
        return "redirect:/";
    }

}
