package com.min.board.controller;

import com.min.board.model.Board;
import com.min.board.model.Comment;
import com.min.board.paging.Pagination;
import com.min.board.service.CommentService;
import com.min.board.service.PagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

// 댓글 관련 컨트롤러
//@RestController
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PagingService pagingService;

    @GetMapping("/board/comments")
    public String commentList(Model model,
                              @RequestParam(required = false, defaultValue = "1") int page,
                              @RequestParam(required = false, defaultValue = "1") int range,
                              Principal principal) throws Exception {
        String loginUser = principal.getName();

        Pagination pagination = pagingService.getCommentPagination(page, range, loginUser);
        List<Comment> comments = commentService.userCommentList(pagination);

        model.addAttribute("pagination", pagination);
        model.addAttribute("comments", comments);

        return "/board/commentList";
    }

    // 댓글 조회
    @GetMapping("/comments/{boardId}")
    @ResponseBody
    public List<Comment> getCommentList(@PathVariable(value = "boardId") Long boardId) throws Exception {
        List<Comment> comments = commentService.getCommentList(boardId);
        return comments;
    }

    // 댓글 작성
    @PostMapping("/comments/{boardId}")
    @ResponseBody
    public void commentWrite(@PathVariable(value = "boardId") Long boardId,
                             @RequestBody Comment comment,
                               Principal principal) throws Exception {
        String username = principal.getName();
        commentService.write(boardId, comment.getContent(), username);
    }

    // 댓글 수정
    @PatchMapping("/comments/{commentId}")
    @ResponseBody
    public void updateComment(@PathVariable(value = "commentId") Long commentId,
                              @RequestBody Comment comment) throws Exception {
        commentService.update(commentId, comment.getContent());
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    @ResponseBody
    public void deleteComment(@PathVariable(value = "commentId") Long commentId) throws Exception {
        commentService.delete(commentId);
    }
}
