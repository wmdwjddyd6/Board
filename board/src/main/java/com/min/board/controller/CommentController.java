package com.min.board.controller;

import com.min.board.model.Comment;
import com.min.board.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

// 댓글 관련 컨트롤러
@RequestMapping("/comments")
@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    // 댓글 조회
    @GetMapping("/{boardId}")
    public List<Comment> getCommentList(@PathVariable(value = "boardId") Long boardId) throws Exception {
        List<Comment> comments = commentService.getCommentList(boardId);
        return comments;
    }

    // 댓글 작성
    @PostMapping("/{boardId}")
    public void commentWrite(@PathVariable(value = "boardId") Long boardId,
                             @RequestBody Comment comment,
                               Principal principal) throws Exception {
        String username = principal.getName();
        commentService.write(boardId, comment.getContent(), username);
    }

    // 댓글 수정
    @PatchMapping("/{commentId}")
    public void updateComment(@PathVariable(value = "commentId") Long commentId,
                              @RequestBody Comment comment) throws Exception {
        commentService.update(commentId, comment.getContent());
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable(value = "commentId") Long commentId) throws Exception {
        commentService.delete(commentId);
    }
}
