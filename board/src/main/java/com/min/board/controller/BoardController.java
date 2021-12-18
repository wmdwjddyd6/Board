package com.min.board.controller;

import com.min.board.model.Board;
import com.min.board.model.Member;
import com.min.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    // 게시판 리스트 페이징 및 검색 리스트
    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 2) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String searchText) {
        Page<Board> boards = boardService.search(searchText, searchText, pageable);
        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 4);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("boards", boards);

        return "board/list";
    }

    // 게시글 작성 폼 & 게시글 불러와서 수정
    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id) {
        if(id == null) {
            model.addAttribute("board", new Board());
        } else {
            Board board = boardService.contentLoad(id);
            System.out.println(board.getMember().getUsername());
            model.addAttribute("board", board);
        }

        return "board/form";
    }

    // 게시글 작성
    @PostMapping("/form")
    public String boardSubmit(@Valid Board board, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "board/form";
        }

        boardService.save(board);

        return "redirect:/board/list";
    }

    // 게시글 삭제
    @PostMapping("/delete")
    public String boardDelete(Long boardId) {
        boardService.delete(boardId);

        return "redirect:/board/list";
    }

//    @GetMapping("/userinfo")
//    public String test(Model model, Principal principal){
//        String loginUsername = principal.getName();
//        model.addAttribute("loginUsername", loginUsername);
//
//        return "board/list";
//    }
}
