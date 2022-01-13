package com.min.board.controller;

import com.min.board.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private MemberService memberService;


    // 회원계정 삭제
    @ResponseBody
    @DeleteMapping("/members")
    public void deleteMembers(@RequestParam(name = "memberIdList[]", required = false) List<String> memberIdList) {
        memberService.deleteMember(memberIdList);
    }
}
