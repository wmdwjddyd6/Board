package com.min.board.repository;

import com.min.board.model.Member;
import com.min.board.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MyMemberRepositoryTest {

    @Autowired
    private MemberRepository mr;
    @Autowired
    private MemberService ms;

    // 회원가입 테스트
    @Test
    public void save_테스트(){
        Member member = new Member();

        member.setUsername("김광민");
        member.setPassword("wmdwjdd");
        member.setRole("admin");

        mr.save(member);
    }
}