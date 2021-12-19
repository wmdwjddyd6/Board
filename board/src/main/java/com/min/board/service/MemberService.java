package com.min.board.service;

import com.min.board.model.Member;
import com.min.board.repository.JpaMemberRepository;
import com.min.board.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
        // 일반 dataSource를 사용한 JDBC
//    private final MemberRepository memberRepository;
//
//    @Autowired
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    private final JpaMemberRepository jpaMemberRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public MemberService(JpaMemberRepository jpaMemberRepository) {
        this.jpaMemberRepository = jpaMemberRepository;
    }

    public void join(Member member){
        jpaMemberRepository.save(member);
    }

    public String pwdEncoding(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    public Long getUserId(String username) {
        return jpaMemberRepository.findByUsername(username).getId();
    }

    public boolean checkUsername(String username) {
        return jpaMemberRepository.existsByUsername(username);
    }
}
