package com.min.board.service;

import com.min.board.model.Member;
//import com.min.board.model.MemberID;
import com.min.board.repository.MemberMapper;
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

    private final MemberMapper memberRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public MemberService(MemberMapper memberMapper) {
        this.memberRepository = memberMapper;
    }

    // 회원가입
    public void join(Member member){
        if(checkUsername(member.getUsername())) { // 아이디 중복 2차 체크 (1차는 컨트롤러의 Validator에서 진행)
            System.out.println("memberService.join() : 이미 존재하는 아이디입니다.");
        } else {
            try {
                memberRepository.save(member);
                System.out.println("memberService.join() : 회원가입 완료");
            } catch (Exception e) {
                System.out.println("memberService.join() .. error : " + e.getMessage());
            }
        }
    }

    // 회원탈퇴
    public void secession(String username) {
        Member member = getMember(username);

        try{
            memberRepository.delete(member);
            System.out.println("memberService.secession() : 회원탈퇴 완료");
        } catch (Exception e) {
            System.out.println("memberService.secession() .. error : " + e.getMessage());
        }
    }

    // 비밀번호 체크
    public boolean checkPassword(String loginUsername, String password) {
        Member member = getMember(loginUsername);

        if(bCryptPasswordEncoder.matches(password, member.getPassword())) {
            System.out.println("memberService.checkPassword() : 암호가 일치합니다.");
            return true;
        } else {
            System.out.println("memberService.checkPassword() : 암호가 일치하지 않습니다.");
            return false;
        }
    }

    // 비밀번호 변경
    public void changePassword(String loginUsername, String newPassword) {
        String encPassword = pwdEncoding(newPassword);
        Member member = getMember(loginUsername);
        member.setPassword(encPassword);

        try{
            memberRepository.pwdChange(member);
            System.out.println("memberService.changePassword() : Password 변경 완료");
        } catch (Exception e) {
            System.out.println("memberService.changePassword() .. error : " + e.getMessage());
        }
    }

    // 패스워드 암호화
    public String pwdEncoding(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    public Member getMember(String username) {
        Member member = new Member();

        try{
            member = memberRepository.findByUsername(username);
            System.out.println("memberService.getMember() : 멤버 객체 초기화 완료");
            return member;
        } catch (Exception e) {
            System.out.println("memberService.getMember() .. error : " + e.getMessage());
            return member;
        }
    }

    // 아이디 중복 체크
    public boolean checkUsername(String username) {
        // true 반환 시 중복 아이디 있음
        Member member = getMember(username);

        try {
            if(member.getUsername().equals(username)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("error : " + e.getMessage()); // DB 조회 결과가 없을 때 error : null 출력
            return false;
        }
    }
}
