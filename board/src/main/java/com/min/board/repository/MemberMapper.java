package com.min.board.repository;

import com.min.board.model.Member;
import com.min.board.paging.Pagination;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {

    // 이름으로 유저 정보 받기
    Member findByUsername(String username) throws Exception;

    // 회원탈퇴
    int delete(Member member) throws Exception;

    // 회원가입
    int save(Member member) throws Exception;

    // 비밀번호 변경
    void pwdChange(Member member) throws Exception;

    // 이메일로 유저 정보 받기
    List<Member> findByEmail(String email) throws Exception;

    // (관리자 회원관리 페이징) 해당 페이지 회원정보 받기
    List<Member> selectMemberList(Pagination pagination) throws Exception;

    // (관리자 회원관리 페이징) 회원수 받기
    int selectMemberTotalCount(Pagination pagination) throws Exception;

    // id로 회원 정보 받기
    Member findById(Long id) throws Exception;
}
