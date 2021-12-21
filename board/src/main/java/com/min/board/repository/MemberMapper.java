package com.min.board.repository;

import com.min.board.model.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {
    // 모든 유저 정보 받기
    List<Member> selectAllMember();

    // 이름으로 유저 정보 받기
    Member findByUsername(String username);

    // 회원탈퇴
    void delete(Member member);

    // 회원가입
    void save(Member member);

    // 비밀번호 변경
    void pwdChange(Member member);
}
