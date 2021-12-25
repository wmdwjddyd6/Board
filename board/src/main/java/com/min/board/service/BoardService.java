package com.min.board.service;

import com.min.board.model.Board;
import com.min.board.paging.Pagination;
import com.min.board.repository.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class BoardService {

    private final BoardMapper boardRepository;

    @Autowired
    public BoardService(BoardMapper boardMapper) {
        this.boardRepository = boardMapper;
    }

//    // 검색 : 제목과 내용으로 검색
//    public Page<Board> search(String title, String content, Pageable pageable) {
//        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(title, content, pageable);
//        return boards;
//    }

//    // 내가 쓴 글 리스트 뽑기
//    public Page<Board> possessionContentLoad(Pageable pageable, Member member) {
//        EntityManager entityManager = null;
//        Page<Board> boards = (Page) entityManager.find(Board.class, member);
//        return boards;
//    }

    // 전체 게시글 개수 리턴
    public int getBoardListCnt() {
        int boardTotalCount = 0;

        try {
            boardTotalCount = boardRepository.selectBoardTotalCount(); // 파라미터 안넘겨도 될 듯??

//            PaginationInfo paginationInfo = new PaginationInfo(board);
//            paginationInfo.setTotalRecordCount(boardTotalCount);
//
//            board.setPaginationInfo(paginationInfo);
//
//            if(boardTotalCount > 0) {
//                boards = boardRepository.selectBoardList(board);
//            }
        } catch (Exception e) {
            System.out.println("boardRepository.getBoardListCnt() .. error : " + e.getMessage());
        } finally {
            return boardTotalCount;
        }
    }

    public List<Board> getBoardList(Pagination pagination) {
        List<Board> boards = Collections.emptyList();

        try {
            boards = boardRepository.selectBoardList(pagination);
        } catch (Exception e) {
            System.out.println("boardRepository.getBoardList() .. error : " + e.getMessage());
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
    public void save(Board board) {
        board.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
        try {
            boardRepository.insertBoard(board);
        } catch (Exception e) {
            System.out.println("boardService.save() .. error : " + e.getMessage());
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
