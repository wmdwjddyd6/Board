package com.min.board.repository;

import com.min.board.model.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JpaMemberRepositoryTest {

    @Autowired
    JpaMemberRepository jpaMemberRepository;

    // 회원탈퇴_delete 테스트
    @Test
    public void delete(){
        Member member = new Member();
        member.setId(8l);
        member.setUsername("tlgjadyd");

        jpaMemberRepository.delete(member);
    }
}