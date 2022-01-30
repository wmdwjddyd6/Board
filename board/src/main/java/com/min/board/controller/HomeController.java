package com.min.board.controller;

import com.min.board.model.MemberDto;
import com.min.board.paging.Pagination;
import com.min.board.service.BoardService;
import com.min.board.service.MemberService;
import com.min.board.service.PagingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/*
*
* 일반 유저와 관리자의 홈 화면 컨트롤러
* 관리자 홈의 경우 [회원관리]
*
* */
@Controller
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberService memberService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private PagingService pagingService;

    // 게시판 메인 홈
    @GetMapping("/")
    public String Home() {
        return "index";
    }

    // 관리자 홈
    @GetMapping("/admin")
    public String adminHome(Model model,
                            @RequestParam(required = false, defaultValue = "1") int page,
                            @RequestParam(required = false, defaultValue = "1") int range,
                            String searchText) throws Exception {
        Pagination pagination = pagingService.getMemberPagination(page, range, searchText);
        List<MemberDto> members = memberService.getMemberList(pagination);

        Map<MemberDto,Long> memberMap = new LinkedHashMap<>(); // 순서가 있는 HashMap을 사용

        // 각 회원의 게시글 개수를 put
        for(MemberDto memberDto : members) {
            Long boardCount = boardService.getSpecificBoardCnt(memberDto.getId());
            memberMap.put(memberDto, boardCount);
        }

        model.addAttribute("memberMap", memberMap);
        model.addAttribute("pagination", pagination);

        return "admin/adminHome";
    }
}
