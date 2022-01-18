package com.min.board.controller;

import com.min.board.model.Board;
import com.min.board.model.FileDTO;
import com.min.board.paging.Pagination;
import com.min.board.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.security.Principal;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private FileService fileService;

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

        Pagination pagination = pagingService.getBoardPagination(page, range, searchText, "list");
        List<Board> boards = boardService.getBoardList(pagination);

        model.addAttribute("pagination", pagination);
        model.addAttribute("boardList", boards);

        return "board/list";
    }

    // 공지사항 메뉴 (조회)
    @GetMapping("/noticeList")
    public String notice(Model model,
                         @RequestParam(required = false, defaultValue = "1") int page,
                         @RequestParam(required = false, defaultValue = "1") int range,
                         String searchText) {
        Pagination pagination = pagingService.getBoardPagination(page, range, searchText, "notice");
        List<Board> boards = boardService.getBoardList(pagination);

        model.addAttribute("pagination", pagination);
        model.addAttribute("boardList", boards);

        return "board/noticeList";
    }

    // 포스트 조회
    @GetMapping("/noticePost")
    public String readNotice(Model model, @RequestParam(required = false) Long boardId,
                             Principal principal, HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        String loginUser = principal.getName();
        Board board = boardService.contentLoad(boardId, "notice");

        boardService.updateViews(board, loginUser, request, response, "notice");

        model.addAttribute("board", board);
        model.addAttribute("loginUser", loginUser);

        return "board/noticePost";
    }

    // 게시글 신규 작성 폼 진입 & 기존 게시글 불러오기
    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long boardId) {
        if (boardId == null) {
            model.addAttribute("board", new Board());
        } else {
            Board board = boardService.contentLoad(boardId, "board");
            model.addAttribute("board", board);
        }

        return "board/form";
    }

    // 게시글 작성 & 수정
    @PostMapping("/form")
    public String boardSubmit(@Valid Board board, BindingResult bindingResult, Principal principal,
                              @RequestParam(value = "files", required = false) List<MultipartFile> files,
                              @RequestParam(value = "boardId", required = false) Long boardId) throws IOException, SQLException {
        if (bindingResult.hasErrors() || (!CollectionUtils.isEmpty(files) && files.size() > 7)) {
            return "board/form";
        }

        String loginUsername = principal.getName();
        String type = "board";

        if (boardId == null) { // 새 글 작성
            Long newBoardId = boardService.save(board, loginUsername, type); // Insert

            // 첨부파일 있을 때
            if (!files.get(0).getOriginalFilename().isEmpty()) {
                for (int i = 0; i < files.size(); i++) {
                    if (files.get(i).getContentType().contains("image/")) {
                        fileService.saveFile(files.get(i), newBoardId);
                    } else {
                        System.out.println("이미지 타입이 아닙니다");
                    }
                }
            }
        } else { // 기존 글 수정
            boardService.update(board, boardId, type); // Update
        }

        return "redirect:/board/list";
    }


    // 포스트 조회
    @GetMapping("/post")
    public String readPost(Model model, @RequestParam(required = false) Long boardId,
                           Principal principal, HttpServletRequest request,
                           HttpServletResponse response) throws Exception {
        String loginUser = principal.getName();
        Board board = boardService.contentLoad(boardId, "board");

        boardService.updateViews(board, loginUser, request, response, "board");

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

        Pagination pagination = pagingService.getBoardPagination(page, range, loginUser, "myPost");
        List<Board> boards = boardService.getBoardList(pagination);

        model.addAttribute("pagination", pagination);
        model.addAttribute("boardList", boards);

        return "board/myPost";
    }

    // 글 관리에서 삭제
    @PostMapping("/myPost/delete")
    public String boardDelete(@RequestParam(required = false) List<String> boardIdList) {
        if (boardIdList == null) return "redirect:/board/myPost";
        if (boardIdList.size() > 0) {
            for (int i = 0; i < boardIdList.size(); i++) {
                boardService.temporaryDelete(Long.parseLong(boardIdList.get(i)));
            }
        }

        return "redirect:/board/myPost";
    }
}
