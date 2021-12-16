package com.min.board.service;

import com.min.board.model.Member;
import com.min.board.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberRepository MemberRepository;

    @Autowired
    MemberService memberService;

    @Test
    public void join_회원가입() {
        Member member = new Member();

        member.setUsername("22김광민");
        member.setPassword("d22fklsjfd");
        member.setRole("admin");

        memberService.join(member);
    }

}