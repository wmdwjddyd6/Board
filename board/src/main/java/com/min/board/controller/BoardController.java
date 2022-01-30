package com.min.board.controller;

import com.min.board.model.BoardDto;
import com.min.board.paging.Pagination;
import com.min.board.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

/*
* 
* 게시판 관련 컨트롤러
*
* Pagination : paging을 위한 객체
* result : DB 작업이 정상적으로 완료됐는지 체크
* 
* */
@Controller
@RequestMapping("/board")
public class BoardController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
                       String searchText) throws Exception {

        Pagination pagination = pagingService.getBoardPagination(page, range, searchText, "list");
        List<BoardDto> boards = boardService.getBoardList(pagination);

        model.addAttribute("pagination", pagination);
        model.addAttribute("boardList", boards);

        return "board/list";
    }

    // 공지사항 메뉴 (조회)
    @GetMapping("/noticeList")
    public String notice(Model model,
                         @RequestParam(required = false, defaultValue = "1") int page,
                         @RequestParam(required = false, defaultValue = "1") int range,
                         String searchText) throws Exception {
        Pagination pagination = pagingService.getBoardPagination(page, range, searchText, "notice");
        List<BoardDto> boards = boardService.getBoardList(pagination);

        model.addAttribute("pagination", pagination);
        model.addAttribute("boardList", boards);

        return "board/noticeList";
    }

    // 공지사항 조회
    @GetMapping("/noticePost")
    public String readNotice(Model model, @RequestParam(required = false) Long boardId,
                             Principal principal, HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        String loginUser = principal.getName();
        BoardDto boardDto = boardService.contentLoad(boardId, "notice");
        logger.debug("boardId : {} 공지사항 조회", boardDto.getId());

        boardService.updateViews(boardDto, request, response, "notice"); // 쿠키를 이용한 조회수 증가

        model.addAttribute("board", boardDto);
        model.addAttribute("loginUser", loginUser);

        return "board/noticePost";
    }

    // 공지사항 삭제
    @PostMapping("/notice/delete")
    public String noticeDelete(@RequestParam(required = false) Long boardId) throws Exception {
        int result = boardService.temporaryDelete(boardId);
        if (result > 0) {
            logger.info("boardId : {} 공지사항 임시 삭제 완료", boardId);
        }
        return "redirect:/board/noticeList";
    }

    // 게시글 신규 작성 폼 진입 & 기존 게시글 불러오기
    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long boardId) throws Exception {
        if (boardId == null) {  
            // 새 글 작성
            model.addAttribute("board", new BoardDto());
            logger.debug("새 글 작성으로 이동");
        } else {    
            // 기존 게시글 수정
            BoardDto boardDto = boardService.contentLoad(boardId, "board");
            logger.debug("boardId : {} 글 수정으로 이동", boardDto.getId());
            model.addAttribute("board", boardDto);
        }
        return "board/form";
    }

    // 게시글 작성 & 수정
    @PostMapping("/form")
    public String boardSubmit(BoardDto boardDto, Principal principal,
                              @RequestParam(value = "files", required = false) List<MultipartFile> files,
                              @RequestParam(value = "boardId", required = false) Long boardId) throws Exception {
        if (boardDto.getTitle().length() < 1 || boardDto.getContent().length() < 1 || (!CollectionUtils.isEmpty(files) && files.size() > 7)) {
            // 잘못된 입력값이 들어왔을 때 다시 해당 페이지로 로딩
            if (boardId != null) {
                return "redirect:/board/form?boardId=" + boardId;
            }
            return "redirect:/board/form";
        }

        String loginUsername = principal.getName();
        String type = "board";

        if (boardId == null) { // 새 글 작성
            Long newBoardId = boardService.save(boardDto, loginUsername, type); // Insert
            logger.info("boardId : {} 글을 작성했습니다.", newBoardId);

            // 첨부파일 있을 때 처리
            if (!files.get(0).getOriginalFilename().isEmpty()) {
                for (int i = 0; i < files.size(); i++) {
                    if (files.get(i).getContentType().contains("image/")) { // 이미지 타입 체크
                        fileService.saveFile(files.get(i), newBoardId); // 첨부파일 서버에 저장 및 관련 정보 DB에 저장
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
        return "redirect:/board/list";
    }


    // 게시글 조회
    @GetMapping("/post")
    public String readPost(Model model, @RequestParam(required = false) Long boardId,
                           Principal principal, HttpServletRequest request,
                           HttpServletResponse response) throws Exception {
        String loginUser = principal.getName();
        BoardDto boardDto = boardService.contentLoad(boardId, "board");
        logger.debug("boardId : {} 글 조회", boardDto.getId());

        boardService.updateViews(boardDto, request, response, "board"); // 쿠키를 이용한 조회수 증가

        model.addAttribute("board", boardDto);
        model.addAttribute("loginUser", loginUser);

        return "board/post";
    }

    // 게시글 삭제
    @PostMapping("/delete")
    public String boardDelete(@RequestParam(required = false) Long boardId) throws Exception {
        int result = boardService.temporaryDelete(boardId);
        if (result > 0) {
            logger.info("boardId : " + boardId + " 임시 삭제 완료");
        }
        return "redirect:/board/list";
    }

    // 내가 쓴 글 화면 이동
    @GetMapping("/myPost")
    public String myPost(Model model,
                         @RequestParam(required = false, defaultValue = "1") int page,
                         @RequestParam(required = false, defaultValue = "1") int range,
                         Principal principal) throws Exception {
        String loginUser = principal.getName();

        Pagination pagination = pagingService.getBoardPagination(page, range, loginUser, "myPost");
        List<BoardDto> boards = boardService.getBoardList(pagination);

        model.addAttribute("pagination", pagination);
        model.addAttribute("boardList", boards);

        return "board/myPost";
    }

    // 글 관리에서 삭제 (영구삭제가 아닌 DB의 delete_yn = 'Y'로 변경)
    @PostMapping("/myPost/delete")
    public String boardDelete(@RequestParam(required = false) List<String> boardIdList) throws Exception {
        if (boardIdList == null) return "redirect:/board/myPost";
        if (boardIdList.size() > 0) {
            for (int i = 0; i < boardIdList.size(); i++) {
                int result = boardService.temporaryDelete(Long.parseLong(boardIdList.get(i)));
                if (result > 0) {
                    logger.info("boardId : " + boardIdList.get(i) + " 임시 삭제 완료");
                }
            }
        }
        return "redirect:/board/myPost";
    }
}
