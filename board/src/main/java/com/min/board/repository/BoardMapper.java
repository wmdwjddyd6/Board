package com.min.board.repository;

import com.min.board.model.BoardDto;
import com.min.board.paging.Pagination;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*
*
* 게시판 기능 관련 Repository
* MyBatis 사용 : resources - mapper에 xml을 통해 sql 관리
*
* */
@Mapper
public interface BoardMapper {
    // 게시글 개수 반환 (메인 게시글, 글 관리, 휴지통)
    int selectBoardTotalCount(Pagination pagination) throws Exception;

    // 게시글 리스트 반환 (메인 게시글, 글 관리, 휴지통)
    List<BoardDto> selectBoardList(Pagination pagination) throws Exception;

    // 게시글 수정
    void updateBoard(BoardDto boardDto) throws Exception;

    // 조회수 증가
    void updateViews(BoardDto boardDto) throws Exception;

    // 아이디로 글 찾기
    BoardDto findById(BoardDto boardDto) throws Exception;

    // 휴지통으로 이동 (임시삭제)
    int temporaryDeleteById(Long id) throws Exception;

    // 글 작성
    void insertBoard(BoardDto boardDto) throws Exception;

    // 휴지통 복원
    int restoreDeleteById(Long boardId) throws Exception;

    // 휴지통 비우기
    int permanentlyDeleteById(Long boardId) throws Exception;

    // 특정 유저 게시글 개수
    Long boardCnt(Long writerId) throws Exception;
}
