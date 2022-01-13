package com.min.board.service;

import com.min.board.model.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
//@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    // 회원가입 테스트
    @Test
    public void join_회원가입() {
        Member member = new Member();

        member.setUsername("rhkdrhkd");
        member.setPassword("123456");
        member.setEmail("wmdwjdd@naver.com");

        int result = 0;

        try {
            result = memberService.join(member, "ROLE_USER");
        } catch (Exception e) {
            System.out.println("회원가입 실패");
        }
    }

    // 비밀번호 확인 & 변경 테스트
    @Test
    public void changePassword_비밀번호변경() {
        Member member = memberService.getMember("rhkdrhkd"); // 회원 객체 받기
        String loginUsername = "";

        try{
            loginUsername = member.getUsername();
        } catch (Exception e) {
            System.out.println("멤버 객체가 없습니다.");
            return;
        }

        String oldPassword = "123456";
        String newPassword = "789456123";

        if(oldPassword.isEmpty() || newPassword.isEmpty()) {
            System.out.println("암호를 정확하게 입력해주세요.");
        } else if(memberService.checkPassword(loginUsername, oldPassword)) { // 이전 비밀번호 체크
            System.out.println("이전 비밀번호와 일치합니다.");
            memberService.changePassword(loginUsername, newPassword); // 비밀번호 변경
//            SecurityContextHolder.clearContext();

            System.out.println("변경 완료");
        } else {
            System.out.println("알 수 없는 오류입니다.");
        }
    }

    // 회원탈퇴 테스트
    @Test
    public void secession_회원탈퇴() {
        Member member = memberService.getMember("rhkdrhkd");

        try {
            memberService.secession(member.getUsername());
            System.out.println("회원탈퇴 완료");
        } catch (Exception e) {
            System.out.println("회원탈퇴 실패");
        }
    }

    // 아이디 중복 체크
    @Test
    public void checkUsername_아이디중복체크() {
        if(memberService.checkUsername("rhkdrhkd")) {
            System.out.println("존재하는 아이디입니다.");
        }else {
            System.out.println("없는 아이디입니다.");
        }
    }

    // ID 찾기 테스트
    @Test
    public void getMemberByEmail() {
        Member member = new Member();
        member.setEmail("test@nate.com");

        String resultUsername = "";
        List<Member> memberList = memberService.getMemberByEmail(member.getEmail());

        member.setUsername(resultUsername);

        System.out.println(resultUsername);
    }

    @Test
    public void getRandomPassword() {
        String password = memberService.getRandomPassword("rhkdrhkd");
        System.out.println(password);
    }
}