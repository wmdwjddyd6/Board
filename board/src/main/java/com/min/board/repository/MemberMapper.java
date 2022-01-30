package com.min.board.repository;

import com.min.board.model.MemberDto;
import com.min.board.paging.Pagination;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*
 *
 * 사용자 기능 관련 Repository
 * MyBatis 사용 : resources - mapper에 xml을 통해 sql 관리
 *
 * */
@Mapper
public interface MemberMapper {

    // 이름으로 유저 정보 받기
    MemberDto findByUsername(String username) throws Exception;

    // 회원탈퇴
    int delete(MemberDto memberDto) throws Exception;

    // 회원가입
    int save(MemberDto memberDto) throws Exception;

    // 비밀번호 변경
    void pwdChange(MemberDto memberDto) throws Exception;

    // 이메일로 유저 정보 받기
    List<MemberDto> findByEmail(String email) throws Exception;

    // (관리자 회원관리 페이징) 해당 페이지 회원정보 받기
    List<MemberDto> selectMemberList(Pagination pagination) throws Exception;

    // (관리자 회원관리 페이징) 회원수 받기
    int selectMemberTotalCount(Pagination pagination) throws Exception;

    // id로 회원 정보 받기
    MemberDto findById(Long id) throws Exception;
}
