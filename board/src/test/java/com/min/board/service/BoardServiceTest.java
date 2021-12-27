package com.min.board.service;

import com.min.board.model.Board;
import com.min.board.model.Member;
import com.min.board.paging.Pagination;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    public BoardService boardService;

    @Autowired
    public MemberService memberService;

    @Test
    public void save_게시글n개생성() {
        Board board = new Board();
        String username = "rhkdrhkd";

        for(int i = 0; i < 215; i ++) {
            board.setTitle(i + "번째 제목입니다.");
            board.setContent(i + "번째 내용이에요.");

            boardService.save(board, username);
        }

    }

    @Test
    public void contentLoad_컨텐츠로드() {
        Board board = boardService.contentLoad(5l);
    }

    @Test
    public void deleteBoard_게시글휴지통으로() {
        boardService.temporaryDelete(6l);
    }

    @Test
    public void getBoardList_메인게시판리스트() {
        int page = 62;
        int range = (page / 10) + 1;
        String searchText = "검색 데이터";

        Pagination pagination = new Pagination();
        pagination.setSearchText(searchText);
        pagination.setType("list");
        int listCount = boardService.getBoardListCnt(pagination);
        pagination.pageInfo(page, range, listCount);

        List<Board> boards = boardService.getBoardList(pagination);
        for(int i = 0; i < boards.size(); i ++) {
            System.out.println("board : " + boards.get(i)); // for test
        }
    }
}