package com.min.board.repository;

import com.min.board.model.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardMapper {
    Board selectBoardById(Long id);
    List<Board> selectAllBoards();
    void insertBoard(Board board);
    void updateBoard(Board board);

    Board findById(Long id);
    void temporaryDeleteById(Long id);
}
