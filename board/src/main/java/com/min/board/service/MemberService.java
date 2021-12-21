package com.min.board.service;

import com.min.board.model.Member;
//import com.min.board.model.MemberID;
import com.min.board.repository.JpaMemberRepository;
import com.min.board.repository.MemberMapper;
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

    private final MemberMapper memberRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public MemberService(MemberMapper memberMapper) {
        this.memberRepository = memberMapper;
    }

    // 회원가입
    public void join(Member member){
        memberRepository.save(member);
    }

    // 회원탈퇴
    public void secession(String username) {
        Member member = memberRepository.findByUsername(username);

        memberRepository.delete(member);
    }

    // 비밀번호 체크
    public boolean checkPassword(String loginUsername, String password) {
        Member member = memberRepository.findByUsername(loginUsername);

        if(bCryptPasswordEncoder.matches(password, member.getPassword())) {
            return true;
        }
        return false;
    }

    // 비밀번호 변경
    public void changePassword(String loginUsername, String newPassword) {
        String encPassword = pwdEncoding(newPassword);
        Member member = memberRepository.findByUsername(loginUsername);
        member.setPassword(encPassword);

        memberRepository.pwdChange(member);
    }

    // 패스워드 암호화
    public String pwdEncoding(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    public Member getMember(String username) {
        return memberRepository.findByUsername(username);
    }

    public Long getUserId(String username) {
        return memberRepository.findByUsername(username).getId();
    }

    public String getUserEmail(String username) {
        return memberRepository.findByUsername(username).getEmail();
    }

    // 아이디 중복 체크
    public boolean checkUsername(String username) {
        try {
            if(memberRepository.findByUsername(username).getUsername().equals(username)) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
