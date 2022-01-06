package com.min.board.repository.mapper;

import com.min.board.model.Board;
import com.min.board.paging.Pagination;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardMapper {
    // 모든 글 조회
    List<Board> selectAllBoards();

    // 게시글 개수 반환 (메인 게시글, 글 관리, 휴지통)
    int selectBoardTotalCount(Pagination pagination);

    // 게시글 리스트 반환 (메인 게시글, 글 관리, 휴지통)
    List<Board> selectBoardList(Pagination pagination);

    // 게시글 수정
    void updateBoard(Board board);

    // 조회수 증가
    void updateViews(Long id) throws Exception;

    // 아이디로 글 찾기
    Board findById(Long id);

    // 휴지통으로 이동 (임시삭제)
    void temporaryDeleteById(Long id);

    // 글 작성
    void insertBoard(Board board);

    // 휴지통 복원
    void restoreDeleteById(Long boardId);

    // 휴지통 비우기
    void permanentlyDeleteById(Long boardId);
}
