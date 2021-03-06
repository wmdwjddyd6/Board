package com.min.board.controller;

import com.min.board.model.CommentDto;
import com.min.board.paging.Pagination;
import com.min.board.service.CommentService;
import com.min.board.service.PagingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/*
*
* 댓글 관련 컨트롤러
*
* 내 글 관리의 댓글관리를 제외한
* [작성, 조회, 수정, 삭제]를 REST API로 구현
*
* Pagination : paging을 위한 객체
* result : DB 작업이 정상적으로 완료됐는지 체크
*
* */
@Controller
public class CommentController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CommentService commentService;

    @Autowired
    private PagingService pagingService;

    // 댓글 리스트 (내 정보 메뉴)
    @GetMapping("/board/comments")
    public String commentList(Model model,
                              @RequestParam(required = false, defaultValue = "1") int page,
                              @RequestParam(required = false, defaultValue = "1") int range,
                              Principal principal) throws Exception {
        String loginUser = principal.getName();

        Pagination pagination = pagingService.getCommentPagination(page, range, loginUser);
        List<CommentDto> comments = commentService.userCommentList(pagination);

        model.addAttribute("pagination", pagination);
        model.addAttribute("comments", comments);

        return "board/commentList";
    }

    // 댓글 조회
    @GetMapping("/comments/{boardId}")
    @ResponseBody
    public List<CommentDto> getCommentList(@PathVariable(value = "boardId") Long boardId) throws Exception {
        List<CommentDto> comments = commentService.getCommentList(boardId);
        logger.debug("boardId : {} 게시글의 댓글을 조회합니다.", boardId);
        return comments;
    }

    // 공지사항 댓글 작성
    @PostMapping("/notice/comments/{boardId}")
    @ResponseBody
    public void noticeCommentWrite(@PathVariable(value = "boardId") Long boardId,
                             @RequestBody CommentDto commentDto,
                             Principal principal) throws Exception {
        String username = principal.getName();
        int result = commentService.write(boardId, commentDto.getContent(), "notice", username);
        if(result > 0) logger.info("boardId : {}에 댓글을 작성했습니다.", boardId);
    }

    // 일반 댓글 작성
    @PostMapping("/comments/{boardId}")
    @ResponseBody
    public void commentWrite(@PathVariable(value = "boardId") Long boardId,
                             @RequestBody CommentDto commentDto,
                             Principal principal) throws Exception {
        String username = principal.getName();
        int result = commentService.write(boardId, commentDto.getContent(), "board", username);
        if(result > 0) logger.info("boardId : {}에 댓글을 작성했습니다.", boardId);
    }

    // 댓글 수정
    @PatchMapping("/comments/{commentId}")
    @ResponseBody
    public void updateComment(@PathVariable(value = "commentId") Long commentId,
                              @RequestBody CommentDto commentDto) throws Exception {
        int result = commentService.update(commentId, commentDto.getContent());
        if(result > 0) logger.info("commentId : {} 댓글을 수정했습니다.", commentId);
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    @ResponseBody
    public void deleteComment(@PathVariable(value = "commentId") Long commentId) throws Exception {
        int result = commentService.delete(commentId);
        if(result > 0) logger.info("commentId : {} 댓글을 삭제했습니다.", commentId);
    }
}
