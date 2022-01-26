package com.min.board.service;

import com.min.board.model.Member;
import com.min.board.paging.Pagination;
import com.min.board.repository.MemberMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/*
 *
 * 사용자 관련 서비스
 * 
 * result : DB 작업이 정상적으로 완료됐는지 체크
 *
 * */
@Service
public class MemberService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MemberMapper memberRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public MemberService(MemberMapper memberMapper) {
        this.memberRepository = memberMapper;
    }

    // 회원가입
    public int join(Member member, String role) throws Exception {
        int result = 0;

        if (checkUsername(member.getUsername())) { // 아이디 중복 2차 체크 (Validator로 1차 진행)
            logger.info(member.getUsername() + "는 이미 존재하는 아이디입니다.");
        } else {
            member.setPassword(pwdEncoding(member.getPassword())); // PW 암호화 및 저장
            member.setCreateDate(Timestamp.valueOf(LocalDateTime.now())); // 회원가입 시간
            member.setRole(role);

            result = memberRepository.save(member);

            logger.info(member.getUsername() + " 회원가입 완료");
        }

        return result;
    }

    // 회원탈퇴
    public void secession(String username) throws Exception {
        Member member = getMember(username);
        int result = memberRepository.delete(member);

        if(result > 0) logger.info(username + " : 회원탈퇴 완료");
    }

    // 비밀번호 체크
    public boolean checkPassword(String loginUsername, String password) {
        Member member = getMember(loginUsername);

        if (bCryptPasswordEncoder.matches(password, member.getPassword())) {
            logger.info(loginUsername + " : 암호가 일치합니다.");
            return true;
        } else {
            logger.info(loginUsername + "암호가 일치하지 않습니다.");
            return false;
        }
    }

    // 비밀번호 변경
    public void changePassword(String username, String newPassword) throws Exception {
        String encPassword = pwdEncoding(newPassword);
        Member member = getMember(username);
        member.setPassword(encPassword);

        memberRepository.pwdChange(member);

        logger.info(username + " : Password 변경 완료");
    }

    // 패스워드 암호화
    public String pwdEncoding(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    // 이름으로 유저 정보 반환
    public Member getMember(String username) {
        Member member = new Member();
        try {
            member = memberRepository.findByUsername(username);
        } catch (Exception e) {
            logger.warn(username + " : 존재하지 않는 username입니다.");
            logger.warn("error : " + e.getMessage());
        }
        return member;
    }

    // 아이디 중복 체크
    public boolean checkUsername(String username) {
        Member member = getMember(username);
        boolean state = false;

        try {
            if (member.getUsername().equals(username)) {
                logger.debug(username + " : 중복된 아이디입니다.");
                state = true;
            }
        } catch (NullPointerException e) {
            logger.debug(username + " : 중복된 아이디가 없습니다.");
        }
        return state;
    }

    // ID 찾기 Email 체크
    public boolean checkEmail(String email) throws Exception {
        List<Member> member = getMemberByEmail(email);
        boolean state = false;

        try {
            if (member.get(0).getEmail().equals(email)) {
                logger.debug(email + " : 이메일이 존재합니다.");
                state = true;
            } else {
                logger.debug(email + " : 이메일이 존재하지 않습니다.");
            }
        } catch (Exception e) {
            logger.debug(email + " : 이메일이 존재하지 않습니다.");
        }
        return state;
    }

    // 등록된 Email의 유저 리스트 받기
    public List<Member> getMemberByEmail(String email) throws Exception {
        List<Member> member = Collections.emptyList();
        member = memberRepository.findByEmail(email);

        return member;
    }

    // password 리셋을 위한 email, username 비교
    public boolean compareEmailUsername(String username, String email) {
        Member member = getMember(username);

        if (member.getEmail().equals(email)) {
            logger.info("username = {}, email = {} 정보가 일치합니다.", username, email);
            return true;
        } else {
            logger.info("username = {}, email = {} 정보가 일치하지 않습니다.", username, email);
            return false;
        }
    }

    // 임시 비밀번호 발급 & DB에 저장
    public String getRandomPassword(String username) throws Exception {
        // 랜덤 PW 생성 준비
        char[] charSet = new char[]{
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
                'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
                'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
                'y', 'z', '!', '@', '#', '$', '%', '^', '&'};
        StringBuffer sb = new StringBuffer();
        SecureRandom sr = new SecureRandom();
        sr.setSeed(new Date().getTime());

        int index = 0;
        int len = charSet.length;

        // 랜덤 PW 10자리 생성
        for (int i = 0; i < 10; i++) {
            index = sr.nextInt(len);
            sb.append(charSet[index]);
        }

        // 임시 비밀번호 저장
        String temporaryPassword = sb.toString();

        changePassword(username, temporaryPassword);
        logger.info(username + "님의 임시 비밀번호를 저장했습니다.");

        return temporaryPassword;
    }

    // (관리자 회원관리 페이징) 해당 페이지 회원정보 리턴
    public List<Member> getMemberList(Pagination pagination) throws Exception {
        List<Member> memberList = memberRepository.selectMemberList(pagination);
        return memberList;
    }

    // (관리자 회원관리 페이징) 회원수 카운트
    public int getMemberListCnt(Pagination pagination) throws Exception {
        return memberRepository.selectMemberTotalCount(pagination);
    }

    // (관리자) 회원계정 삭제
    public void deleteMember(List<String> id) throws Exception {
        for (String memberId : id) {
            Member member = memberRepository.findById(Long.parseLong(memberId));
            memberRepository.delete(member);
            logger.info(member.getUsername() + "님의 계정을 삭제했습니다.");
        }
    }
}
