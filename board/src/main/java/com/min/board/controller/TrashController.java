package com.min.board.controller;

import com.min.board.model.Board;
import com.min.board.paging.Pagination;
import com.min.board.service.BoardService;
import com.min.board.service.PagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class TrashController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private PagingService pagingService;

    // 휴지통 화면 이동
    @GetMapping("/trash")
    public String trash(Model model,
                        @RequestParam(required = false, defaultValue = "1") int page,
                        @RequestParam(required = false, defaultValue = "1") int range,
                        Principal principal) {
        String loginUser = principal.getName();

        Pagination pagination = pagingService.getBoardPagination(page, range, loginUser, "trash");
        List<Board> boards = boardService.getBoardList(pagination);

        model.addAttribute("pagination", pagination);
        model.addAttribute("boardList", boards);

        return "board/trash";
    }

    // 휴지통 게시글 복원
    @ResponseBody
    @PatchMapping("/trash")
    public void restoreBoards(@RequestParam(name = "boardIdList[]", required = false) List<String> boardIdList) {
        for(int i = 0; i < boardIdList.size(); i ++) {
            boardService.restore(Long.parseLong(boardIdList.get(i)));
        }
    }

    // 휴지통 게시글 영구 삭제
    @ResponseBody
    @DeleteMapping("/trash")
    public void clearBoards(@RequestParam(name = "boardIdList[]", required = false) List<String> boardIdList) {
        for(int i = 0; i < boardIdList.size(); i ++) {
            boardService.clear(Long.parseLong(boardIdList.get(i)));
        }
    }
}
