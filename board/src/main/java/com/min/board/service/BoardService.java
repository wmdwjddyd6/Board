package com.min.board.service;

import com.min.board.model.Board;
import com.min.board.model.Member;
import com.min.board.repository.BoardMapper;
import com.min.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    private final BoardMapper boardMapper;

    @Autowired
    public BoardService(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

//    // 검색 : 제목과 내용으로 검색
//    public Page<Board> search(String title, String content, Pageable pageable) {
//        Page<Board> boards = boardMapper.findByTitleContainingOrContentContaining(title, content, pageable);
//        return boards;
//    }

    // 내가 쓴 글 리스트 뽑기
    public Page<Board> possessionContentLoad(Pageable pageable, Member member) {
        EntityManager entityManager = null;
        Page<Board> boards = (Page) entityManager.find(Board.class, member);
        return boards;
    }

    // id를 이용해서 해당 글 수정
    public Board contentLoad(Long id) {
        Board board = boardMapper.findById(id);
        return board;
    }

    // 글 등록
    public void save(Board board) {

        boardMapper.insertBoard(board); // 이게 문제네
        System.out.println("여기2");
    }

    // 글 삭제
    public void temporaryDelete(Long boardId) {
        boardMapper.temporaryDeleteById(boardId);
    }
}
