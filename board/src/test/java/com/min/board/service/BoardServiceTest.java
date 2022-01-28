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

    @Autowired
    public PagingService pagingService;

    @Test
    public void save_게시글n개생성() throws Exception {
        Board board = new Board();
        String username = "rhkdals123";

        for(int i = 2; i < 80; i ++) {
            board.setTitle(i + "번째 제목입니다.");
            board.setContent(i + "번째 내용이에요.");

            boardService.save(board, username, "board");
        }
    }

    @Test
    public void contentLoad_컨텐츠로드() throws Exception {
        Board board = boardService.contentLoad(5l, "board");
    }

    @Test
    public void deleteBoard_게시글휴지통으로() throws Exception {
        int result = boardService.temporaryDelete(6l);
        System.out.println(result);
    }

    @Test
    public void getBoardList_메인게시판리스트() throws Exception {
        int page = 62;
        int range = (page / 10) + 1;
        String searchText = "검색 데이터";

        Pagination pagination = pagingService.getBoardPagination(page, range, searchText, "list");
        List<Board> boards = boardService.getBoardList(pagination);

        for(int i = 0; i < boards.size(); i ++) {
            System.out.println("board : " + boards.get(i)); // for test
        }
    }

    @Test
    public void getBoardList_휴지통() throws Exception {
        int page = 2;
        int range = (page / 10) + 1;

        String loginUser = "rhkdrhkd";
        Pagination pagination = new Pagination();
        pagination.setType("trash");
        pagination.setWriter(loginUser);
        int listCount = boardService.getBoardListCnt(pagination);
        pagination.pageInfo(page, range, listCount);

        List<Board> boards = boardService.getBoardList(pagination);
        for(int i = 0; i < boards.size(); i ++) {
            System.out.println("board : " + boards.get(i)); // for test
        }
    }

}