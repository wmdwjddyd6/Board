package com.min.board.service;

import com.min.board.model.Board;
import com.min.board.model.Member;
import com.min.board.paging.Pagination;
import com.min.board.repository.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

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

    // 내 글 관리 - 전체 게시글 개수 리턴
    public int getMyDeleteBoardListCnt(String writer) {
        int boardTotalCount = 0;

        try {
            boardTotalCount = boardRepository.selectDeleteMyBoardTotalCount(writer);
        } catch (Exception e) {
            System.out.println("boardRepository.selectDeleteMyBoardTotalCount() .. error : " + e.getMessage());
        } finally {
            return boardTotalCount;
        }
    }

    // 내 글 관리 - 전체 게시글 리스트로 리턴
    public List<Board> getMyDeleteBoardList(Pagination pagination) {
        List<Board> boards = Collections.emptyList();

        try {
            boards = boardRepository.selectDeleteMyBoardList(pagination);
        } catch (Exception e) {
            System.out.println("boardRepository.selectDeleteMyBoardTotalCount() .. error : " + e.getMessage());
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

    // 글 등록
    public void save(Board board, String loginUsername) {
        board.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));

        try {
            Member member = memberService.getMember(loginUsername);

            board.setWriterId(member.getId());
            board.setWriter(member.getUsername());

            boardRepository.insertBoard(board);
        } catch (Exception e) {
            System.out.println("boardService.save() .. error : " + e.getMessage());
        }
    }

    public void update(Board board, Long boardId) {
        board.setId(boardId);

        try {
            boardRepository.updateBoard(board);
        } catch (Exception e) {
            System.out.println("boardService.update() .. error : " + e.getMessage());
        }

    }

    // 글 임시 삭제 (업데이트 로직 실행)
    public void temporaryDelete(Long boardId) {
        try {
            boardRepository.temporaryDeleteById(boardId);
        } catch (Exception e) {
            System.out.println("boardService.temporaryDelete() .. error : " + e.getMessage());
        }
    }
}
