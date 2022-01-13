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
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberValidator memberValidator;

    // 관리자 계정 생성 폼
    @GetMapping("/admin/addAdmin")
    public String addAdminForm(Model model) {
        model.addAttribute("member", new Member());
        return "/admin/addAdmin";
    }

    // 관리자 계정 생성
    @PostMapping("/admin/join")
    public String addMember(@Valid Member member, BindingResult bindingResult){ // view의 form->input 의 name과 매핑됨.
        memberValidator.validate(member, bindingResult);

        if(bindingResult.hasErrors()) {
            return "/admin/addAdmin";
        }

        int result = memberService.join(member, "ROLE_ADMIN");
        if(result > 0) {
            return "redirect:/admin";
        } else {
            return "/admin/addAdmin";
        }
    }

    // 회원계정 삭제
    @ResponseBody
    @DeleteMapping("/members")
    public void deleteMembers(@RequestParam(name = "memberIdList[]", required = false) List<String> memberIdList) {
        memberService.deleteMember(memberIdList);
    }
}
