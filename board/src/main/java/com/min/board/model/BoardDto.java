package com.min.board.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class BoardDto {
    private Long id;
    private String title;
    private String content;
    private Long writerId;
    private String writer;
    private String deleteYN;
    private Timestamp createDate;
    private Long views;
    private String type;
}
