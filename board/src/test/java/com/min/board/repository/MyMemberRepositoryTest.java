package com.min.board.repository;

import com.min.board.domain.Member;
import com.min.board.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MyMemberRepositoryTest {

    @Autowired
    private MemberRepository mr;
    @Autowired
    private MemberService ms;

    @Test
    public void save_테스트(){
        Member member = new Member();

        member.setUsername("김광민");
        member.setUserId("wmdwjdd");
        member.setPassword("123456");

        mr.save(member);

        System.out.println(member.toString());
    }
}