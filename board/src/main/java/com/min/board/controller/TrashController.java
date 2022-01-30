package com.min.board.controller;

import com.min.board.model.BoardDto;
import com.min.board.paging.Pagination;
import com.min.board.service.BoardService;
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
* 휴지통 관련 컨트롤러
*
* 글의 [복원, 영구삭제]를 REST API로 구현
*  - 관리자 화면의 회원관리에서 재사용
*
* */
@Controller
public class TrashController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BoardService boardService;

    @Autowired
    private PagingService pagingService;

    // 휴지통 화면 이동
    @GetMapping("/board/trash")
    public String trash(Model model,
                        @RequestParam(required = false, defaultValue = "1") int page,
                        @RequestParam(required = false, defaultValue = "1") int range,
                        Principal principal) throws Exception {
        String loginUser = principal.getName();

        Pagination pagination = pagingService.getBoardPagination(page, range, loginUser, "trash");
        List<BoardDto> boards = boardService.getBoardList(pagination);

        model.addAttribute("pagination", pagination);
        model.addAttribute("boardList", boards);

        return "board/trash";
    }

    // 휴지통 게시글 복원
    @ResponseBody
    @PatchMapping("/trash")
    public void restoreBoards(@RequestParam(name = "boardIdList[]", required = false) List<String> boardIdList) throws Exception {
        for(int i = 0; i < boardIdList.size(); i ++) {
            int result = boardService.restore(Long.parseLong(boardIdList.get(i)));
            if(result > 0) logger.info("boardId : {} 를 휴지통에서 복원했습니다.", boardIdList.get(i));
        }
    }

    // 휴지통 게시글 영구 삭제
    @ResponseBody
    @DeleteMapping("/trash")
    public void clearBoards(@RequestParam(name = "boardIdList[]", required = false) List<String> boardIdList) throws Exception {
        for(int i = 0; i < boardIdList.size(); i ++) {
            int result = boardService.clear(Long.parseLong(boardIdList.get(i)));
            if(result > 0) logger.info("boardId : {} 를 휴지통에서 삭제했습니다.", boardIdList.get(i));
        }
    }
}
