package com.min.board.model;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "tb_board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 30, message = "제목은 2자 이상 30자 이하입니다.")
    private String title;

    @NotNull
    @Size(min = 1, message = "내용을 입력하세요.")
    private String content;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
