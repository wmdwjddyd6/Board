package com.min.board.model;

import java.sql.Timestamp;

public class Comment {
    private Long cid;
    private Long bid;
    private String content;
    private Long writer_id;
    private String writer;
    private Timestamp createDate;
}
