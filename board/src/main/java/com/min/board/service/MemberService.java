package com.min.board.service;

import com.min.board.model.Member;
//import com.min.board.model.MemberID;
import com.min.board.paging.Pagination;
import com.min.board.repository.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
                member.setPassword(pwdEncoding(member.getPassword()));
                member.setCreateDate(Timestamp.valueOf(LocalDateTime.now())); // 회원가입 시간
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
    public void changePassword(String username, String newPassword) {
        String encPassword = pwdEncoding(newPassword);
        Member member = getMember(username);
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

    // 이름으로 멤버 저장
    public Member getMember(String username) {
        Member member = new Member();

        try{
            member = memberRepository.findByUsername(username);
            System.out.println("memberService.getMember() : 멤버 객체 초기화 완료");
        } catch (Exception e) {
            System.out.println("memberService.getMember() .. error : " + e.getMessage());
        } finally {
            return member;
        }
    }

    // 아이디 중복 체크
    public boolean checkUsername(String username) {
        Member member = getMember(username);

        try {
            if(member.getUsername().equals(username)) {     // true 반환 시 중복 아이디 있음
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("memberService.checkUsername() .. error : " + e.getMessage()); // DB 조회 결과가 없을 때 error : null 출력
            return false;
        }
    }

    // ID 찾기 Email 체크
    public boolean checkEmail(String email) {
        List<Member> member = getMemberByEmail(email);

        try {
            if(member.get(0).getEmail().equals(email)) {       // true 반환 시 존재하는 이메일
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("memberService.checkEmail() .. error : " + e.getMessage());
            return false;
        }
    }

    // 등록된 Email의 유저 리스트 받기
    public List<Member> getMemberByEmail(String email) {
        List<Member> member = Collections.emptyList();

        try {
            member = memberRepository.findByEmail(email);
        } catch (Exception e) {
            System.out.println("memberService.getMemberByEmail() .. error : " + e.getMessage());
        } finally {
            return member;
        }
    }

    // password 리셋을 위한 email, username 비교
    public boolean compareEmailUsername(String username, String email) {
        Member member = getMember(username);

        if(member.getEmail().equals(email)) {
            return true;
        } else {
            return false;
        }
    }

    // 임시 비밀번호 발급 & DB에 저장
    public String getRandomPassword(String username) {
        char[] charSet = new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
                'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
                'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
                'y', 'z', '!', '@', '#', '$', '%', '^', '&' };
        StringBuffer sb = new StringBuffer();
        SecureRandom sr = new SecureRandom();
        sr.setSeed(new Date().getTime());

        int index = 0;
        int len = charSet.length;

        for (int i = 0; i<10; i++) {
            index = sr.nextInt(len);
            sb.append(charSet[index]);
        }

        String temporaryPassword = sb.toString(); // 임시 비밀번호

        changePassword(username, temporaryPassword);

        return temporaryPassword;
    }

    // (관리자 회원관리 페이징) 해당 페이지 회원정보 리턴
    public List<Member> getMemberList(Pagination pagination) {
        List<Member> memberList = memberRepository.selectMemberList(pagination);
        return memberList;
    }

    // (관리자 회원관리 페이징) 회원수 카운트
    public int getMemberListCnt(Pagination pagination) {
        return memberRepository.selectMemberTotalCount(pagination);
    }

    // (관리자) 회원계정 삭제
    public void deleteMember(List<String> id) {
        for(String memberId : id) {
            Member member = memberRepository.findById(Long.parseLong(memberId));
            memberRepository.delete(member);
        }
    }
}
