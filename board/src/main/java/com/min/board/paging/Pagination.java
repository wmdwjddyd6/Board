package com.min.board.paging;

import lombok.Data;

@Data
public class Pagination {

    private int listSize = 10;  // 초기값으로 목록개수를 10으로 셋팅
    private int rangeSize = 10; // 초기값으로 페이지범위를 10으로 셋팅
    private int page;           // 현재 페이지
    private int range;          // 현재 페이지 범위 (1~10 = 1)
    private int listCnt;        // 총 게시물 개수
    private int pageCnt;        // 총 페이지 개수
    private int startPage;      // 각 페이지 범위 중 시작 번호
    private int startList;      // mysql용 게시판 시작 번호 (0, 10, 20..)
    private int endPage;        // 각 페이지 범위 중 마지막 번호
    private boolean prev;       // 이전 페이지 여부
    private boolean next;       // 다음 페이지 여부

    public void pageInfo(int page, int range, int listCnt) {

        this.page = page; // 현재 페이지
        this.listCnt = listCnt; // 게시물 개수 총합

        // 페이지 범위
        this.range = range;

        // 전체 페이지수
        this.pageCnt = (int) Math.ceil((double) listCnt / listSize);

        // 시작 페이지
        this.startPage = (range - 1) * rangeSize + 1 ;

        // 끝 페이지
        this.endPage = range * rangeSize;

        // 게시판 시작번호
        this.startList = (page - 1) * listSize;

        // 이전 버튼 상태
        this.prev = range == 1 ? false : true;

        // 다음 버튼 상태
        this.next = endPage > pageCnt ? false : true;

        if(this.endPage > this.pageCnt) {
            this.endPage = this.pageCnt;
            this.next = false;
        }
    }
}
