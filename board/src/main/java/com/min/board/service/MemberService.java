package com.min.board.service;

import com.min.board.model.Member;
import com.min.board.model.MemberID;
import com.min.board.repository.JpaMemberRepository;
import com.min.board.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

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

    // 회원가입
    public void join(Member member){
        jpaMemberRepository.save(member);
    }

    // 회원탈퇴
    public void secession(String username) {
        Member member = jpaMemberRepository.findByUsername(username);
        MemberID memberID = new MemberID(member.getId(), member.getUsername()); // 기본키 셋팅

        jpaMemberRepository.deleteById(memberID);
    }

    // 패스워드 암호화
    public String pwdEncoding(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    public Long getUserId(String username) {
        return jpaMemberRepository.findByUsername(username).getId();
    }

    public String getUserEmail(String username) {
        return jpaMemberRepository.findByUsername(username).getEmail();
    }

    public boolean checkUsername(String username) {
        return jpaMemberRepository.existsByUsername(username);
    }

    // 비밀번호 체크
    public boolean checkPassword(String loginUsername, String password) {
        Member member = jpaMemberRepository.findByUsername(loginUsername);

        if(bCryptPasswordEncoder.matches(password, member.getPassword())) {
            return true;
        }
        return false;
    }

    // 비밀번호 변경
    public void changePassword(String loginUsername, String newPassword) {
        String encPassword = pwdEncoding(newPassword);
        Member member = jpaMemberRepository.findByUsername(loginUsername);
        member.setPassword(encPassword);

        jpaMemberRepository.save(member);
    }
}
