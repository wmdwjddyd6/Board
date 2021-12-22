package com.min.board.service;

import com.min.board.model.Board;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    public BoardService boardService;

    @Autowired
    public MemberService memberService;

    @Test
    public void save_게시글생성() {
        Board board = new Board();
        Long id = 1l; // 작성자 ID
        board.setWriterId(id);
        board.setTitle("하이하이ㅣ");
        board.setContent("안녕하세요");

        boardService.save(board);
    }

    @Test
    public void contentLoad_컨텐츠로드() {
        Board board = boardService.contentLoad(5l);
    }

    @Test
    public void deleteBoard_게시글임시삭제() {
        boardService.temporaryDelete(5l);
    }
}