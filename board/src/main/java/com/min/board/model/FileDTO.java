package com.min.board.model;

import lombok.Data;

@Data
public class FileDto {
    private Long id;
    private Long boardId;
    private String originalFileName;
    private Long size;
    private String path;
    private String storedFileName;

    public FileDto(Long boardId, String originalFileName, String storedFileName, Long size, String path) {
        this.boardId = boardId;
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
        this.size = size;
        this.path = path;
    }
}
