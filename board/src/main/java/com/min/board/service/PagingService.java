package com.min.board.service;

import com.min.board.paging.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagingService {

    @Autowired
    private BoardService boardService;

    @Autowired
    private CommentService commentService;

    // 공통 페이징 처리
    public Pagination getBoardPagination(int page, int range, String keyword, String type) {
        int listCount = 0;
        Pagination pagination = new Pagination();

        if(type.equals("list")) {
            pagination.setSearchText(keyword);
        } else if(type.equals("myPost") || type.equals("trash")) {
            pagination.setWriter(keyword);
        }

        pagination.setType(type);

        listCount = boardService.getBoardListCnt(pagination);

        pagination.pageInfo(page, range, listCount);

        return pagination;
    }

    // 공통 페이징 처리
    public Pagination getCommentPagination(int page, int range, String keyword) throws Exception {
        int listCount = 0;
        Pagination pagination = new Pagination();

        pagination.setWriter(keyword);

        listCount = commentService.countComment(pagination);

        pagination.pageInfo(page, range, listCount);

        return pagination;
    }
}
