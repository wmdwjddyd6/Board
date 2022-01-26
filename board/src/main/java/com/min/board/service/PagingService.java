package com.min.board.service;

import com.min.board.paging.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 *
 * 페이징 관련 서비스
 *
 * [공지사항, 게시글, 회원목록] 등 페이징이 필요한 곳에 사용하기 위해 서비스로 분리
 *
 * */
@Service
public class PagingService {

    @Autowired
    private BoardService boardService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private MemberService memberService;

    // 게시글 관련 페이징 처리
    public Pagination getBoardPagination(int page, int range, String keyword, String type) throws Exception {
        int listCount = 0;
        Pagination pagination = new Pagination();
        pagination.setType(type);

        if(type.equals("list") || type.equals("notice")) {
            pagination.setSearchText(keyword);
        } else if(type.equals("myPost") || type.equals("trash")) {
            pagination.setWriter(keyword);
        }

        listCount = boardService.getBoardListCnt(pagination);

        pagination.pageInfo(page, range, listCount);

        return pagination;
    }

    // 특정 유저 게시글 조회
    public Pagination getMemberBoardPagination(int page, int range, String keyword, String username, String type) throws Exception {
        int listCount = 0;

        Pagination pagination = new Pagination();
        pagination.setType(type);
        pagination.setSearchText(keyword);
        pagination.setWriter(username);

        listCount = boardService.getBoardListCnt(pagination);

        pagination.pageInfo(page, range, listCount);

        return pagination;
    }

    // 댓글 페이징 처리
    public Pagination getCommentPagination(int page, int range, String keyword) throws Exception {
        int listCount = 0;
        Pagination pagination = new Pagination();
        pagination.setWriter(keyword);

        listCount = commentService.getCommentCnt(pagination);

        pagination.pageInfo(page, range, listCount);

        return pagination;
    }

    // 회원 관련 페이징 처리
    public Pagination getMemberPagination(int page, int range, String keyword) throws Exception {
        int listCount = 0;
        Pagination pagination = new Pagination();
        pagination.setSearchText(keyword);

        listCount = memberService.getMemberListCnt(pagination);

        pagination.pageInfo(page, range, listCount);

        return pagination;
    }
}
