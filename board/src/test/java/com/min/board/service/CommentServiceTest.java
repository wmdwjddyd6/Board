package com.min.board.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class CommentServiceTest {

    @Autowired
    CommentService commentService;

    // 댓글 작성 테스트
    @Test
    public void commentWrite() throws Exception{
        String username = "rhkdrhkd1";
        Long boardId = 2l;
        String content = "테스트 댓글";
        commentService.write(boardId, content, username);
    }
}