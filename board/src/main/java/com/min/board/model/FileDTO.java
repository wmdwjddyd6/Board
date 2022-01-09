package com.min.board.model;

import lombok.Data;

@Data
public class FileDTO {
    private Long id;
    private Long boardId;
    private String storedFileName;
    private Long size;
}
