package com.min.board.controller;

import com.min.board.model.Member;
import com.min.board.service.MemberService;
import com.min.board.validator.MemberValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/*
*
* 회원가입 관련 컨트롤러
*
* result : DB 작업이 정상적으로 완료됐는지 체크
*
* */
@Controller
@RequestMapping("/register")
public class JoinController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
    public String join(@Valid Member member, BindingResult bindingResult) throws Exception { // view의 form->input 의 name과 매핑됨.
        // @Valid와 validate를 이용해서 사용자 입력값을 검증
        memberValidator.validate(member, bindingResult);

        if(bindingResult.hasErrors()) {
            return "register/joinForm";
        }

        logger.debug("회원가입을 진행합니다.");
        int result = memberService.join(member, "ROLE_USER");

        if(result > 0) {
            logger.info("회원가입이 완료됐습니다.");
            return "redirect:/loginForm";
        } else {
            logger.warn("회원가입에 실패했습니다.");
            return "register/joinForm";
        }
    }
}
