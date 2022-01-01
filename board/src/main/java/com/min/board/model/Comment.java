package com.min.board.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Comment {
    private Long id;
    private Long boardId;
    private String content;
    private Long writerId;
    private String writer;
    private Timestamp createDate;
}
