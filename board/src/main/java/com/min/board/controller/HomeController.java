package com.min.board.controller;

import com.min.board.model.Member;
import com.min.board.paging.Pagination;
import com.min.board.service.BoardService;
import com.min.board.service.MemberService;
import com.min.board.service.PagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

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
                            String searchText) {
        Pagination pagination = pagingService.getMemberPagination(page, range, searchText);
        List<Member> members = memberService.getMemberList(pagination);

        Map<Member,Long> memberMap = new LinkedHashMap<>();

        for(Member member : members) {
            Long boardCount = boardService.getSpecificBoardCnt(member.getId());
            memberMap.put(member, boardCount);
        }

        model.addAttribute("memberMap", memberMap);
        model.addAttribute("pagination", pagination);

        return "admin/adminHome";
    }
}
