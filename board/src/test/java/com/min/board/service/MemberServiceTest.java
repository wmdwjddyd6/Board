package com.min.board.service;

import com.min.board.model.Member;
import com.min.board.model.MemberID;
import com.min.board.repository.JpaMemberRepository;
import com.min.board.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Transactional
class MemberServiceTest {

    @Autowired
    JpaMemberRepository jpaMemberRepository;

    @Autowired
    MemberService memberService;

    @Test
    public void join_회원가입() {
        Member member = new Member();

        member.setUsername("rhkdrhkd");
        member.setPassword("123456");
        member.setEmail("wmdwjdd@naver.com");

        memberService.join(member);
    }

    @Test
    public void changePassword_비밀번호변경() {
        Member member = new Member();

        member.setUsername("rhkdrhkd");
        member.setPassword("123456");
        member.setEmail("wmdwjdd@naver.com");

        memberService.join(member);

        memberService.changePassword(member.getUsername(), "789456123");
    }

}