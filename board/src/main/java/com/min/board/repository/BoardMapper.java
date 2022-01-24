package com.min.board.repository;

import com.min.board.model.Board;
import com.min.board.paging.Pagination;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardMapper {
    // 게시글 개수 반환 (메인 게시글, 글 관리, 휴지통)
    int selectBoardTotalCount(Pagination pagination) throws Exception;

    // 게시글 리스트 반환 (메인 게시글, 글 관리, 휴지통)
    List<Board> selectBoardList(Pagination pagination) throws Exception;

    // 게시글 수정
    void updateBoard(Board board) throws Exception;

    // 조회수 증가
    void updateViews(Board board) throws Exception;

    // 아이디로 글 찾기
    Board findById(Board board) throws Exception;

    // 휴지통으로 이동 (임시삭제)
    int temporaryDeleteById(Long id) throws Exception;

    // 글 작성
    void insertBoard(Board board) throws Exception;

    // 휴지통 복원
    int restoreDeleteById(Long boardId) throws Exception;

    // 휴지통 비우기
    int permanentlyDeleteById(Long boardId) throws Exception;

    // 특정 유저 게시글 개수
    Long boardCnt(Long writerId) throws Exception;
}
