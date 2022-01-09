package com.min.board.service;

import com.min.board.model.Board;
import com.min.board.model.Member;
import com.min.board.paging.Pagination;
import com.min.board.repository.mapper.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public BoardService(BoardMapper boardMapper) {
        this.boardRepository = boardMapper;
    }

    // 전체 게시글 개수 리턴
    public int getBoardListCnt(Pagination pagination) {
        int boardTotalCount = 0;

        try {
            boardTotalCount = boardRepository.selectBoardTotalCount(pagination);
        } catch (Exception e) {
            System.out.println("boardRepository.getBoardListCnt() .. error : " + e.getMessage());
        } finally {
            return boardTotalCount;
        }
    }

    // 전체 게시글 리스트로 리턴
    public List<Board> getBoardList(Pagination pagination) {
        List<Board> boards = Collections.emptyList();

        try {
            boards = boardRepository.selectBoardList(pagination);
        } catch (Exception e) {
            System.out.println("boardRepository.getMyBoardList() .. error : " + e.getMessage());
        } finally {
            return boards;
        }
    }

    // id를 이용해서 해당 글 수정
    public Board contentLoad(Long id) {
        Board board = new Board();
        try {
            board = boardRepository.findById(id);
        } catch (Exception e) {
            System.out.println("boardService.contentLoad() .. error : " + e.getMessage());
        } finally {
            return board;
        }
    }

    // 조회수 증가
    public void updateViews(Long id, String username, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        Cookie[] cookies = request.getCookies();
        Map<String, String> mapCookie = new HashMap<>();

        if(request.getCookies() != null) {
            for(int i = 0; i < cookies.length; i ++) {
                mapCookie.put(cookies[i].getName(), cookies[i].getValue());
            }

            String viewsCookie = mapCookie.get("views");
            String newCookie = "|" + id;

            // 쿠키가 없을 경우 쿠키 생성 후 조회수 증가
            if(viewsCookie == null || !viewsCookie.contains(Long.toString(id))) {
                Cookie cookie = new Cookie("views", viewsCookie + newCookie);
                response.addCookie(cookie);

                boardRepository.updateViews(id);
            }
        }
    }

    // 글 등록
    public Long save(Board board, String loginUsername) {
        board.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));

        try {
            Member member = memberService.getMember(loginUsername);

            board.setWriterId(member.getId());
            board.setWriter(member.getUsername());

            boardRepository.insertBoard(board);
        } catch (Exception e) {
            System.out.println("boardService.save() .. error : " + e.getMessage());
        }
        return board.getId();
    }

    // 글 수정
    public Long update(Board board, Long boardId) {
        board.setId(boardId);

        try {
            boardRepository.updateBoard(board);
        } catch (Exception e) {
            System.out.println("boardService.update() .. error : " + e.getMessage());
        }
        return board.getId();
    }

    // 글 임시 삭제 (업데이트 로직 실행)
    public void temporaryDelete(Long boardId) {
        try {
            boardRepository.temporaryDeleteById(boardId);
        } catch (Exception e) {
            System.out.println("boardService.temporaryDelete() .. error : " + e.getMessage());
        }
    }

    // 휴지통 복원
    public void restore(Long boardId) {
        try {
            boardRepository.restoreDeleteById(boardId);
        } catch (Exception e) {
            System.out.println("boardService.restore() .. error : " + e.getMessage());
        }
    }

    // 휴지통 비우기
    public void clear(Long boardId) {
        try {
            boardRepository.permanentlyDeleteById(boardId);
        } catch (Exception e) {
            System.out.println("boardService.clear() .. error : " + e.getMessage());
        }
    }
}
