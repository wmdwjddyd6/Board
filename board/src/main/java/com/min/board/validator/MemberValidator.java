package com.min.board.validator;

import com.min.board.model.MemberDto;
import com.min.board.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/*
 *
 * 회원가입 조건 검사
 *
 * */
@Component
public class MemberValidator implements Validator {

    @Autowired
    private MemberService memberService;

    @Override
    public boolean supports(Class<?> clazz) {
        return MemberDto.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        MemberDto memberDto = (MemberDto) obj;

        if(memberService.checkUsername(memberDto.getUsername())) { // 회원이 존재하는지 체크
            errors.rejectValue("username", "key", "이미 존재하는 아이디입니다.");
        }
    }
}
