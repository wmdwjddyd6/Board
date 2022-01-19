package com.min.board.repository;

import com.min.board.model.Member;
import com.min.board.paging.Pagination;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {

    // 이름으로 유저 정보 받기
    Member findByUsername(String username);

    // 회원탈퇴
    void delete(Member member);

    // 회원가입
    int save(Member member);

    // 비밀번호 변경
    void pwdChange(Member member);

    // 이메일로 유저 정보 받기
    List<Member> findByEmail(String email);

    // (관리자 회원관리 페이징) 해당 페이지 회원정보 받기
    List<Member> selectMemberList(Pagination pagination);

    // (관리자 회원관리 페이징) 회원수 받기
    int selectMemberTotalCount(Pagination pagination);

    // id로 회원 정보 받기
    Member findById(Long id);
}
