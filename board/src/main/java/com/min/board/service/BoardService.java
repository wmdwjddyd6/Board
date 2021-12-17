package com.min.board.service;

import com.min.board.model.Board;
import com.min.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 검색 : 제목과 내용으로 검색
    public Page<Board> search(String title, String content, Pageable pageable) {
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(title, content, pageable);
        return boards;
    }

    // id를 이용해서 해당 글 수정
    public Board contentLoad(Long id) {
        Board board = boardRepository.findById(id).orElse(null);
        return board;
    }

    // 글 등록
    public void save(Board board) {
        boardRepository.save(board);
    }
}
