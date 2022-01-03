package com.min.board.controller;

import com.min.board.model.Comment;
import com.min.board.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

// 댓글 관련 컨트롤러
@Controller
@RequestMapping("/board/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // 댓글 작성
    @PostMapping("/write")
    public String commentWrite(@RequestParam(required = false) Long boardId, String content,
                               Principal principal) throws Exception {
        String username = principal.getName();
        commentService.write(boardId, content, username);

        return "redirect:/board/post?id=" + boardId;
    }

    // 댓글 조회
    @GetMapping("/getCommentList")
    @ResponseBody
    public List<Comment> getCommentList(@RequestParam(name = "boardId") Long boardId) throws Exception {
        List<Comment> comments = commentService.getCommentList(boardId);
        return comments;
    }

    // 댓글 수정
    @PostMapping("/update")
    @ResponseBody
    public void updateComment(@RequestParam(name = "commentId") Long commentId,
                              @RequestParam(name = "content") String content) throws Exception {
        commentService.update(commentId, content);
    }

    // 댓글 삭제
    @PostMapping("/delete")
    @ResponseBody
    public void deleteComment(@RequestParam(name = "commentId") Long commentId) throws Exception {
        commentService.delete(commentId);
    }
}
