package com.min.board.validator;

import com.min.board.model.Member;
import com.min.board.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

@Component
public class MemeberValidator implements Validator {

    @Autowired
    private MemberService memberService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Member.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Member member = (Member) obj;

        if(memberService.checkUsername(member.getUsername())) {
            errors.rejectValue("username", "key", "이미 존재하는 아이디입니다.");
        }

//        if(member.getUsername().matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
//            errors.rejectValue("username", "key", "영문과 숫자만 입력가능합니다.");
//        }
    }
}
