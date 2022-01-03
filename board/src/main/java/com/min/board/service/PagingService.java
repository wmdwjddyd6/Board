package com.min.board.service;

import com.min.board.paging.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagingService {

    @Autowired
    private BoardService boardService;

    // 공통 페이징 처리
    public Pagination getCommonPagination(int page, int range, String keyword, String type) {
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
}
