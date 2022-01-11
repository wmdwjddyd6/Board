package com.min.board.controller;

import com.min.board.model.FileDTO;
import com.min.board.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
public class FileController {

    @Autowired
    private FileService fileService;

    // 게시글에 첨부된 이미지 ID값 리스트로 반환
    @GetMapping(value = "/images/{boardId}")
    public List<FileDTO> getFileId(@PathVariable(value = "boardId") Long boardId) throws SQLException {
        List<FileDTO> files = fileService.getFileList(boardId);
        return files;
    }

    // 이미지 리턴
    @GetMapping(value = "/image/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> getViewImage(@PathVariable(value = "imageId") Long imageId) throws SQLException {
        FileDTO fileDTO = fileService.getFile(imageId);
        Resource resource = new FileSystemResource(fileDTO.getPath());
        return new ResponseEntity<Resource>(resource, HttpStatus.OK);
    }
}
