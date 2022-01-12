package com.min.board.controller;

import com.min.board.model.Member;
import com.min.board.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private MemberService memberService;

    // 게시판 메인 홈으로
    @GetMapping("/")
    public String Home(){
        return "index";
    }

    // 관리자 홈으로
    @GetMapping("/admin")
    public String adminHome(Model model) {
        List<Member> memberList = memberService.getAllMemberList();
        model.addAttribute("memberList", memberList);
        return "/admin/adminHome";
    }
}
