package com.min.board.service;

import com.min.board.model.BoardDto;
import com.min.board.model.FileDto;
import com.min.board.model.MemberDto;
import com.min.board.paging.Pagination;
import com.min.board.repository.BoardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
*
* 게시판 기능 관련 서비스
*
* type : 게시물 종류 분리 (공지사항 / 일반 게시물)
* result : DB 작업이 정상적으로 완료됐는지 체크
*
* */
@Service
public class BoardService {

    private final BoardMapper boardRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberService memberService;

    @Autowired
    private FileService fileService;

    @Autowired
    public BoardService(BoardMapper boardMapper) {
        this.boardRepository = boardMapper;
    }

    // 전체 게시글 개수 리턴
    public int getBoardListCnt(Pagination pagination) throws Exception {
        int boardTotalCount = 0;
        boardTotalCount = boardRepository.selectBoardTotalCount(pagination);
        logger.debug("boardTotalCount : {}", boardTotalCount);

        return boardTotalCount;
    }

    // 전체 게시글 리스트로 리턴
    public List<BoardDto> getBoardList(Pagination pagination) throws Exception {
        List<BoardDto> boards = Collections.emptyList();
        boards = boardRepository.selectBoardList(pagination);

        return boards;
    }

    // id를 이용해서 해당 글 수정
    public BoardDto contentLoad(Long id, String type) throws Exception {
        BoardDto boardDto = new BoardDto();
        boardDto.setId(id);
        boardDto.setType(type);
        boardDto = boardRepository.findById(boardDto);

        return boardDto;
    }

    // 조회수 증가
    public void updateViews(BoardDto boardDto, HttpServletRequest request,
                            HttpServletResponse response, String type) throws Exception {
        Cookie[] cookies = request.getCookies();
        Map<String, String> mapCookie = new HashMap<>();

        if (request.getCookies() != null) { // 쿠키가 있을 경우
            for (int i = 0; i < cookies.length; i++) {
                mapCookie.put(cookies[i].getName(), cookies[i].getValue()); // 모든 쿠키를 Map 형태로 저장
            }

            String viewsCookie = mapCookie.get("views"); // 저장된 쿠키 중 views 키 값의 value를 가져옴
            String newCookie = type + "|" + boardDto.getId(); // 새롭게 저장할 쿠키 이름 (type으로 [공지사항 / 게시물] 쿠키 분리)

            // views이름의 쿠키가 없거나 조회한 게시물의 번호가 쿠키에 없을 경우 쿠키 생성 후 조회수 증가
            if (viewsCookie == null || !viewsCookie.contains(newCookie)) {
                Cookie cookie = new Cookie("views", viewsCookie + newCookie);
                response.addCookie(cookie); // 쿠키를 추가 후

                boardDto.setType(type);
                boardRepository.updateViews(boardDto); // 조회수 증가
            }
        }
    }

    // 글 등록
    public Long save(BoardDto boardDto, String loginUsername, String type) throws Exception {
        MemberDto memberDto = memberService.getMember(loginUsername);

        boardDto.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
        boardDto.setWriterId(memberDto.getId());
        boardDto.setWriter(memberDto.getUsername());
        boardDto.setType(type);
        boardRepository.insertBoard(boardDto);

        return boardDto.getId();
    }

    // 글 수정
    public Long update(BoardDto boardDto, Long boardId, String type) throws Exception {
        boardDto.setId(boardId);
        boardDto.setType(type);
        boardRepository.updateBoard(boardDto);

        return boardDto.getId();
    }

    // 게시물을 휴지통으로 이동 (업데이트 로직 실행 - delete_yn = 'Y')
    public int temporaryDelete(Long boardId) throws Exception {
        int result = boardRepository.temporaryDeleteById(boardId);
        return result;
    }

    // 휴지통 복원
    public int restore(Long boardId) throws Exception {
        int result = boardRepository.restoreDeleteById(boardId);
        return result;
    }

    // 휴지통 비우기
    public int clear(Long boardId) throws Exception {
        List<FileDto> files = fileService.getFileList(boardId);
        int result = boardRepository.permanentlyDeleteById(boardId);

        // 글 삭제가 정상적으로 됐고, 첨부된 파일이 있다면
        if (result > 0 && !CollectionUtils.isEmpty(files)) {
            // 서버에서 파일 삭제
            for (FileDto fileDTO : files) {
                File file = new File(fileDTO.getPath());
                if (file.exists()) { // 서버에 파일이 존재한다면
                    file.delete(); // 삭제
                    logger.info(fileDTO.getStoredFileName() + " : 삭제 완료");
                } else {
                    logger.debug("삭제할 파일이 없습니다.");
                }
            }
        }
        return result;
    }

    // 특정 유저 게시글 개수
    public Long getSpecificBoardCnt(Long writerId) throws Exception {
        Long count = boardRepository.boardCnt(writerId);
        return count;
    }
}
