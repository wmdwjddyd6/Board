package com.min.board.controller;

import com.min.board.model.Board;
import com.min.board.model.Comment;
import com.min.board.model.Member;
import com.min.board.paging.Pagination;
import com.min.board.service.BoardService;
import com.min.board.service.CommentService;
import com.min.board.service.MemberService;
import com.min.board.service.PagingService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.sql.In;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private PagingService pagingService;

    // 게시판 리스트 (게시글 페이징 및 검색 리스트)
    @GetMapping("/list")
    public String list(Model model,
                       @RequestParam(required = false, defaultValue = "1") int page,
                       @RequestParam(required = false, defaultValue = "1") int range,
                       String searchText) {

        Pagination pagination = pagingService.getCommonPagination(page, range, searchText, "list");
        List<Board> boards = boardService.getBoardList(pagination);

        model.addAttribute("pagination", pagination);
        model.addAttribute("boardList", boards);

        return "board/list";
    }

    // 게시글 신규 작성 폼 진입 & 기존 게시글 불러오기
    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long boardId) {
        if(boardId == null) {
            model.addAttribute("board", new Board());
        } else {
            Board board = boardService.contentLoad(boardId);
            model.addAttribute("board", board);
        }

        return "board/form";
    }

    // 게시글 작성 & 수정
    @PostMapping("/form")
    public String boardSubmit(@Valid Board board, BindingResult bindingResult, Principal principal, Long id) {
        if(bindingResult.hasErrors()) {
            return "board/form";
        }

        String loginUsername = principal.getName();

        if(id == null) {
            boardService.save(board, loginUsername); // Insert
        } else {
            boardService.update(board, id); // Update
        }

        return "redirect:/board/list";
    }

    // 포스트 조회
    @GetMapping("/post")
    public String readPost(Model model, @RequestParam(required = false) Long boardId,
                           Principal principal, HttpServletRequest request,
                           HttpServletResponse response) throws Exception {
        String loginUser = principal.getName();
        Board board = boardService.contentLoad(boardId);

        boardService.updateViews(boardId, loginUser, request, response);

        model.addAttribute("board", board);
        model.addAttribute("loginUser", loginUser);

        return "board/post";
    }

    // 게시글 삭제
    @PostMapping("/delete")
    public String boardDelete(@RequestParam(required = false) Long boardId) {
        boardService.temporaryDelete(boardId);

        return "redirect:/board/list";
    }

    // 내가 쓴 글 화면 이동
    @GetMapping("/myPost")
    public String myPost(Model model,
                       @RequestParam(required = false, defaultValue = "1") int page,
                       @RequestParam(required = false, defaultValue = "1") int range,
                       Principal principal) {
        String loginUser = principal.getName();

        Pagination pagination = pagingService.getCommonPagination(page, range, loginUser, "myPost");
        List<Board> boards = boardService.getBoardList(pagination);

        model.addAttribute("pagination", pagination);
        model.addAttribute("boardList", boards);

        return "board/myPost";
    }

    // 글 관리에서 삭제
    @PostMapping("/myPost/delete")
    public String boardDelete(@RequestParam(required = false) List<String> boardIdList) {
        if(boardIdList == null) return "redirect:/board/myPost";
        if(boardIdList.size() > 0) {
            for(int i = 0; i < boardIdList.size(); i ++) {
                boardService.temporaryDelete(Long.parseLong(boardIdList.get(i)));
            }
        }
        
        return "redirect:/board/myPost";
    }

    // 휴지통 화면 이동
    @GetMapping("/trash")
    public String trash(Model model,
                         @RequestParam(required = false, defaultValue = "1") int page,
                         @RequestParam(required = false, defaultValue = "1") int range,
                         Principal principal) {
        String loginUser = principal.getName();

        Pagination pagination = pagingService.getCommonPagination(page, range, loginUser, "trash");
        List<Board> boards = boardService.getBoardList(pagination);

        model.addAttribute("pagination", pagination);
        model.addAttribute("boardList", boards);

        return "board/trash";
    }

    // 휴지통에서 삭제
    @PostMapping("/trash/delete")
    public String emptyTrash(@RequestParam(required = false) List<String> boardIdList) {
        if(boardIdList == null) return "redirect:/board/trash";
        if(boardIdList.size() > 0) {
            for(int i = 0; i < boardIdList.size(); i ++) {
                boardService.clearBoard(Long.parseLong(boardIdList.get(i)));
            }
        }

        return "redirect:/board/trash";
    }
}
