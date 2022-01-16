package com.min.board.controller;

import com.min.board.model.Board;
import com.min.board.model.Member;
import com.min.board.paging.Pagination;
import com.min.board.service.BoardService;
import com.min.board.service.MemberService;
import com.min.board.service.PagingService;
import com.min.board.validator.MemberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberValidator memberValidator;

    @Autowired
    private BoardService boardService;

    @Autowired
    private PagingService pagingService;

    // 관리자 계정 생성 폼
    @GetMapping("/admin/addAdmin")
    public String addAdminForm(Model model) {
        model.addAttribute("member", new Member());
        return "/admin/addAdmin";
    }

    // 게시글 관리 페이지
    @GetMapping("/admin/boardManage")
    public String allBoardList(Model model,
                               @RequestParam(required = false, defaultValue = "1") int page,
                               @RequestParam(required = false, defaultValue = "1") int range,
                               String searchText) {
        Pagination pagination = pagingService.getBoardPagination(page, range, searchText, "list");
        List<Board> boards = boardService.getBoardList(pagination);

        model.addAttribute("pagination", pagination);
        model.addAttribute("boardList", boards);

        return "/admin/boardManage";
    }

    // 특정 회원 게시글 리스트
    @GetMapping("/admin/userBoard")
    public String userBoard(Model model,
                               @RequestParam(required = false, defaultValue = "1") int page,
                               @RequestParam(required = false, defaultValue = "1") int range,
                               String searchText, String username) {
        Pagination pagination = pagingService.getMemberBoardPagination(page, range, searchText, username, "memberBoardList");
        List<Board> boards = boardService.getBoardList(pagination);

        model.addAttribute("pagination", pagination);
        model.addAttribute("boardList", boards);

        return "/admin/userBoard";
    }

    // 공지사항 관리(조회 메뉴)
    @GetMapping("/admin/notice")
    public String notice(Model model,
                            @RequestParam(required = false, defaultValue = "1") int page,
                            @RequestParam(required = false, defaultValue = "1") int range,
                            String searchText) {
        Pagination pagination = pagingService.getBoardPagination(page, range, searchText, "notice");
        List<Board> boards = boardService.getBoardList(pagination);

        model.addAttribute("pagination", pagination);
        model.addAttribute("boardList", boards);

        return "/admin/notice";
    }

    // 공지사항 수정

   // 관리자 계정 생성
    @PostMapping("/admin/join")
    public String addMember(@Valid Member member, BindingResult bindingResult) { // view의 form->input 의 name과 매핑됨.
        memberValidator.validate(member, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/admin/addAdmin";
        }

        int result = memberService.join(member, "ROLE_ADMIN");

        if (result > 0) {
            return "redirect:/admin";
        } else {
            return "/admin/addAdmin";
        }
    }

    // 회원계정 삭제
    @ResponseBody
    @DeleteMapping("/members")
    public void deleteMembers(@RequestParam(name = "memberIdList[]", required = false) List<String> memberIdList) {
        memberService.deleteMember(memberIdList);
    }
}
