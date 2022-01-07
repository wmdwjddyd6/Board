package com.min.board.service;

import com.min.board.model.Board;
import com.min.board.model.Comment;
import com.min.board.paging.Pagination;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class CommentServiceTest {

    @Autowired
    public CommentService commentService;

    @Autowired
    public PagingService pagingService;

    // 댓글 작성 테스트
    @Test
    public void commentWrite() throws Exception{
        String username = "rhkdrhkd1";
        Long boardId = 2l;
        String content = "테스트 댓글";
        commentService.write(boardId, content, username);
    }

    // 댓글 조회
    @Test
    public void commentShow() throws Exception {
        Long boardId = 3l;
        List<Comment> comments = commentService.getCommentList(boardId);
        for(int i = 0; i < comments.size(); i ++) {
            System.out.println(comments.get(i));
        }
    }

    // 댓글 수정
    @Test
    public void commentUpdate() throws Exception {
        Long commentId = 16l;
        String content = "수정 내용";

        commentService.update(commentId, content);
    }

    // 댓글 리스트
    @Test
    public void getBoardList_메인게시판리스트() throws Exception {
        int page = 1;
        int range = (page / 10) + 1;
        String loginUser = "rhkdals";

        Pagination pagination = pagingService.getCommentPagination(page, range, loginUser);
        List<Comment> comments = commentService.userCommentList(pagination);

        for(int i = 0; i < comments.size(); i ++) {
            System.out.println("comment : " + comments.get(i)); // for test
        }
    }
}