package com.min.board.controller;

import com.min.board.model.BoardDto;
import com.min.board.model.MemberDto;
import com.min.board.paging.Pagination;
import com.min.board.service.BoardService;
import com.min.board.service.FileService;
import com.min.board.service.MemberService;
import com.min.board.service.PagingService;
import com.min.board.validator.MemberValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/*
* 
* 관리자 화면 관련 컨트롤러
*
* Pagination : paging을 위한 객체
* result : DB 작업이 정상적으로 완료됐는지 체크
*
* 회원계정 삭제 (deleteMembers) : REST API로 구현
*
* */
@Controller
public class AdminController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberValidator memberValidator;

    @Autowired
    private BoardService boardService;

    @Autowired
    private FileService fileService;

    @Autowired
    private PagingService pagingService;

    // 관리자 계정 생성 폼
    @GetMapping("/admin/addAdmin")
    public String addAdminForm(Model model) {
        model.addAttribute("member", new MemberDto());
        return "admin/addAdmin";
    }

    // 관리자 계정 생성
    @PostMapping("/admin/join")
    public String addMember(@Valid MemberDto memberDto, BindingResult bindingResult) throws Exception { // view의 form->input 의 name과 매핑됨.
        memberValidator.validate(memberDto, bindingResult);

        if (bindingResult.hasErrors()) {
            return "admin/addAdmin";
        }

        int result = memberService.join(memberDto, "ROLE_ADMIN");

        if (result > 0) {
            logger.info("{} : 관리자 계정을 생성했습니다.", memberDto.getUsername());
            return "redirect:/admin";
        } else {
            return "admin/addAdmin";
        }
    }

    // 게시글 관리 페이지
    @GetMapping("/admin/boardManage")
    public String allBoardList(Model model,
                               @RequestParam(required = false, defaultValue = "1") int page,
                               @RequestParam(required = false, defaultValue = "1") int range,
                               String searchText) throws Exception {
        Pagination pagination = pagingService.getBoardPagination(page, range, searchText, "list");
        List<BoardDto> boards = boardService.getBoardList(pagination);

        model.addAttribute("pagination", pagination);
        model.addAttribute("boardList", boards);

        return "admin/boardManage";
    }

    // 특정 회원 게시글 리스트 (회원 관리)
    @GetMapping("/admin/userBoard")
    public String userBoard(Model model,
                            @RequestParam(required = false, defaultValue = "1") int page,
                            @RequestParam(required = false, defaultValue = "1") int range,
                            String searchText, String username) throws Exception {
        Pagination pagination = pagingService.getMemberBoardPagination(page, range, searchText, username, "memberBoardList");
        List<BoardDto> boards = boardService.getBoardList(pagination);

        model.addAttribute("pagination", pagination);
        model.addAttribute("boardList", boards);

        return "admin/userBoard";
    }

    // 공지사항 관리 (조회 메뉴)
    @GetMapping("/admin/notice")
    public String notice(Model model,
                         @RequestParam(required = false, defaultValue = "1") int page,
                         @RequestParam(required = false, defaultValue = "1") int range,
                         String searchText) throws Exception {
        Pagination pagination = pagingService.getBoardPagination(page, range, searchText, "notice");
        List<BoardDto> boards = boardService.getBoardList(pagination);

        model.addAttribute("pagination", pagination);
        model.addAttribute("boardList", boards);

        return "admin/notice";
    }

    // 공지사항 작성(폼 진입)
    @GetMapping("/admin/noticeForm")
    public String writeNoticeForm(Model model, @RequestParam(required = false) Long boardId) throws Exception {
        if (boardId == null) {
            model.addAttribute("board", new BoardDto());
        } else {
            BoardDto boardDto = boardService.contentLoad(boardId, "notice");
            model.addAttribute("board", boardDto);
        }
        return "admin/noticeForm";
    }

    // 공지사항 작성 & 수정
    @PostMapping("/admin/noticeForm")
    public String writeNotice(BoardDto boardDto, Principal principal,
                              @RequestParam(value = "files", required = false) List<MultipartFile> files,
                              @RequestParam(value = "boardId", required = false) Long boardId) throws Exception {
        if (boardDto.getTitle().length() < 1 || boardDto.getContent().length() < 1 || (!CollectionUtils.isEmpty(files) && files.size() > 7)) {
            // 잘못된 입력값이 들어왔을 때 다시 해당 페이지로 로딩
            if(boardId != null) {
                return "redirect:/admin/noticeForm?boardId=" + boardId;
            }
            return "redirect:/admin/noticeForm";
        }

        String loginUsername = principal.getName();
        String type = "notice";

        if (boardId == null) { // 새 글 작성
            Long newBoardId = boardService.save(boardDto, loginUsername, type); // Insert
            logger.info("boardId : {} 글을 작성했습니다.", newBoardId);

            // 첨부파일 있을 때
            if (!files.get(0).getOriginalFilename().isEmpty()) {
                for (int i = 0; i < files.size(); i++) {
                    if (files.get(i).getContentType().contains("image/")) {
                        fileService.saveFile(files.get(i), newBoardId);
                        logger.info("boardId : {} 글에 첨부파일 {} 를 저장했습니다.", newBoardId, files.get(i).getOriginalFilename());
                    } else {
                        logger.debug("{}는 이미지 타입이 아닙니다.", files.get(i).getOriginalFilename());
                    }
                }
            }
        } else { // 기존 글 수정
            Long id = boardService.update(boardDto, boardId, type); // Update
            logger.info("boardId : {} 글을 수정했습니다.", id);
        }

        return "redirect:/admin/notice";
    }

    // 회원계정 삭제
    @ResponseBody
    @DeleteMapping("/members")
    public void deleteMembers(@RequestParam(name = "memberIdList[]", required = false) List<String> memberIdList) throws Exception {
        memberService.deleteMember(memberIdList);
    }
}
