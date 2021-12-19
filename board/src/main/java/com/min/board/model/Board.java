package com.min.board.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Data
@Table(name = "tb_board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 30, message = "제목은 2자 이상 30자 이하입니다.")
    private String title;

    @NotNull
    @NotBlank(message = "내용을 입력하세요.")
    private String content;

    @ManyToOne(targetEntity = Member.class, fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "writer", referencedColumnName = "username"),
            @JoinColumn(name = "writer_id", referencedColumnName = "id")
    })
    private Member member;
    private String image;
}
