package com.min.board.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.sql.Timestamp;

@Data
public class Board {

    private Long id;

    @NotNull
    @Size(min = 2, max = 30, message = "제목은 2자 이상 30자 이하입니다.")
    private String title;

    @NotNull
    @NotBlank(message = "내용을 입력하세요.")
    private String content;
    private Long writerId;
    private String writer;
    private String deleteYN;
    private Timestamp createDate;

    private String image;
}
