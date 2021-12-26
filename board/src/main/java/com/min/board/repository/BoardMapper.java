package com.min.board.repository;

import com.min.board.model.Board;
import com.min.board.paging.Pagination;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardMapper {
    // 모든 글 조회
    List<Board> selectAllBoards();

    // 삭제된 글 제외 모두 조회
    List<Board> selectBoardList(Pagination pagination);

    // 총 게시글 개수 반환
    int selectBoardTotalCount(String searchText);

    // 게시글 수정
    void updateBoard(Board board);

    // 아이디로 글 찾기
    Board findById(Long id);

    // 휴지통으로 이동 (임시삭제)
    void temporaryDeleteById(Long id);

    // 글 작성
    void insertBoard(Board board);
}
