package com.min.board.service;

import com.min.board.model.Member;
import com.min.board.repository.JpaMemberRepository;
import com.min.board.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    JpaMemberRepository jpaMemberRepository;

    @Autowired
    MemberService memberService;

    @Test
    public void join_회원가입() {
        Member member = new Member();

        member.setUsername("22김광민");
        member.setPassword("d22fklsjfd");
        member.setEmail("wmdwjdd@naver.com");

        memberService.join(member);
    }

    @Test
    public void secession_회원탈퇴() {
        String username = "tlgjadyd";
    }

}