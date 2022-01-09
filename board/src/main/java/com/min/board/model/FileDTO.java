package com.min.board.model;

import lombok.Data;

@Data
public class FileDTO {
    private Long id;
    private Long boardId;
    private String originalFileName;
    private String storedFileName;
    private Long size;

    public FileDTO(String storedFileName, Long size) {
        this.storedFileName = storedFileName;
        this.size = size;
    }
}
