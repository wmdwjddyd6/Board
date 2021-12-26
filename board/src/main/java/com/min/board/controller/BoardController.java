package com.min.board.controller;

import com.min.board.model.Board;
import com.min.board.model.Member;
import com.min.board.paging.Pagination;
import com.min.board.service.BoardService;
import com.min.board.service.MemberService;
import org.dom4j.rule.Mode;
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
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private MemberService memberService;

    // 게시판 리스트      게시글 페이징 및 검색 리스트
    @GetMapping("/list")
    public String list(Model model,
                       @RequestParam(required = false, defaultValue = "1") int page,
                       @RequestParam(required = false, defaultValue = "1") int range,
                       String searchText) {
        int listCount = boardService.getBoardListCnt();

        Pagination pagination = new Pagination();
        pagination.pageInfo(page, range, listCount);

        List<Board> boards = boardService.getBoardList(pagination);

        List<Member> member = memberService.getBoardMatchMember(boards);

        model.addAttribute("pagination", pagination);
        model.addAttribute("boardList", boards);
        model.addAttribute("member", member);

        return "board/list";
    }

//    // 게시글 신규 작성 폼 진입 & 기존 게시글 불러오기
//    @GetMapping("/form")
//    public String form(Model model, @RequestParam(required = false) Long boardId) {
//        if(boardId == null) {
//            model.addAttribute("board", new Board());
//        } else {
//            Board board = boardService.contentLoad(boardId);
//            model.addAttribute("board", board);
//        }
//
//        return "board/form";
//    }

    // 게시글 작성 & 수정
    @PostMapping("/form")
    public String boardSubmit(@Valid Board board, BindingResult bindingResult, Principal principal) {
        if(bindingResult.hasErrors()) {
            return "board/form";
        }
        String loginUsername = principal.getName();

        boardService.save(board, loginUsername); // 이 부분 업데이트랑 인서트 분리해야함 ~~~~~~~~~~~~~~~~~~~~~~~~~~~현재는 인서트임~~~~~~~~~

        return "redirect:/board/list";
    }

    // 포스트 조회
    @GetMapping("/post")
    public String readPost(Model model, @RequestParam(required = false) Long id) {
        Board board = boardService.contentLoad(id);
        model.addAttribute("board", board);
        return "board/post";
    }

    // 게시글 삭제
    @PostMapping("/delete")
    public String boardDelete(Long boardId) {
        boardService.temporaryDelete(boardId);

        return "redirect:/board/list";
    }

//    // 내가 쓴 글 화면 이동
//    @GetMapping("/myPost")
//    public String myPost(Model model, Principal principal, @PageableDefault(size = 5) Pageable pageable) {
//        Member member = memberService.getMember(principal.getName());
//        Page<Board> boards = boardService.possessionContentLoad(pageable, member);
//
//        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
//        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 4);
//
//        model.addAttribute("startPage", startPage);
//        model.addAttribute("endPage", endPage);
//        model.addAttribute("boards", boards);
//
//        return "/board/myPost";
//    }

    // 휴지통 화면 이동
    @GetMapping("/trash")
    public String trashList() {
        return "/board/trash";
    }
}
