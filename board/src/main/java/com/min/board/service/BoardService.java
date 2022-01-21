package com.min.board.service;

import com.min.board.model.Board;
import com.min.board.model.FileDTO;
import com.min.board.model.Member;
import com.min.board.paging.Pagination;
import com.min.board.repository.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoardService {

    private final BoardMapper boardRepository;

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

        return boardTotalCount;
    }

    // 전체 게시글 리스트로 리턴
    public List<Board> getBoardList(Pagination pagination) throws Exception {
        List<Board> boards = Collections.emptyList();
        boards = boardRepository.selectBoardList(pagination);

        return boards;
    }

    // id를 이용해서 해당 글 수정
    public Board contentLoad(Long id, String type) throws Exception {
        Board board = new Board();
        board.setId(id);
        board.setType(type);
        board = boardRepository.findById(board);

        return board;
    }

    // 조회수 증가
    public void updateViews(Board board, String username, HttpServletRequest request,
                            HttpServletResponse response, String type) throws Exception {
        Cookie[] cookies = request.getCookies();
        Map<String, String> mapCookie = new HashMap<>();

        if (request.getCookies() != null) {
            for (int i = 0; i < cookies.length; i++) {
                mapCookie.put(cookies[i].getName(), cookies[i].getValue());
            }

            String viewsCookie = mapCookie.get("views");
            String newCookie = type + "|" + board.getId();

            // 쿠키가 없을 경우 쿠키 생성 후 조회수 증가
            if (viewsCookie == null || !viewsCookie.contains(newCookie)) {
                Cookie cookie = new Cookie("views", viewsCookie + newCookie);
                response.addCookie(cookie);

                board.setType(type);
                boardRepository.updateViews(board);
            }
        }
    }

    // 글 등록
    public Long save(Board board, String loginUsername, String type) throws Exception {
        board.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));

        Member member = memberService.getMember(loginUsername);

        board.setWriterId(member.getId());
        board.setWriter(member.getUsername());
        board.setType(type);
        boardRepository.insertBoard(board);

        return board.getId();
    }

    // 글 수정
    public Long update(Board board, Long boardId, String type) throws Exception {
        board.setId(boardId);
        board.setType(type);
        boardRepository.updateBoard(board);

        return board.getId();
    }

    // 글 임시 삭제 (업데이트 로직 실행)
    public void temporaryDelete(Long boardId) throws Exception {
        boardRepository.temporaryDeleteById(boardId);
    }

    // 휴지통 복원
    public void restore(Long boardId) throws Exception {
        boardRepository.restoreDeleteById(boardId);
    }

    // 휴지통 비우기
    public void clear(Long boardId) throws Exception {
        List<FileDTO> files = fileService.getFileList(boardId);
        int result = boardRepository.permanentlyDeleteById(boardId);

        // 글 삭제가 정상적으로 됐고, 첨부된 파일이 있다면
        if (result > 0 && !CollectionUtils.isEmpty(files)) {
            // 서버에서 파일 삭제
            for (FileDTO fileDTO : files) {
                File file = new File(fileDTO.getPath());
                if (file.exists()) { // 서버에 파일이 존재한다면
                    file.delete(); // 삭제
                    System.out.println(fileDTO.getStoredFileName() + " : 삭제 완료");
                } else {
                    System.out.println("삭제할 파일이 없습니다.");
                    return;
                }
            }
        }
    }

    // 특정 유저 게시글 개수
    public Long getSpecificBoardCnt(Long writerId) throws Exception {
        Long count = boardRepository.boardCnt(writerId);
        return count;
    }
}
