package com.min.board.repository;

import com.min.board.model.MemberID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JpaMemberRepositoryTest {

    @Autowired
    JpaMemberRepository jpaMemberRepository;

    @Test
    public void deleteById(){
        MemberID memberID = new MemberID();
        memberID.setId(8l);
        memberID.setUsername("tlgjadyd");

        jpaMemberRepository.deleteById(memberID);
    }
}