package com.min.board.service;

import com.min.board.model.Board;
import com.min.board.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    public BoardRepository boardRepository;

    @Autowired
    public BoardService boardService;

    @Test
    public void delete_test() {
        boardService.delete(1l);
    }
}