package com.min.board.controller;

import com.min.board.domain.Member;
import com.min.board.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JoinController {

    private MemberService memberService;

    public JoinController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/member/join")
    public String createMember(MemberForm memberForm){
        Member member = new Member();

        member.setName(memberForm.getName());
        member.setUserId(memberForm.getUserId());
        member.setPassword(memberForm.getPassword());

        memberService.join(member);

        return "redirect:/";
    }

}
