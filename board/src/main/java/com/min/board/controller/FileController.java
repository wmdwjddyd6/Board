package com.min.board.controller;

import com.min.board.model.FileDto;
import com.min.board.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/*
*
* 첨부파일(이미지) 관련 컨트롤러
*
* 게시물 조회 시 FrontEnd의 ajax 통신으로 게시물의 id값을 받음. (getFileList)
* ResponseEntity<Resource>로 클라이언트의 각 이미지에 리턴 (getViewImage)
*
* */
@RestController
public class FileController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FileService fileService;

    // 게시글에 첨부된 이미지 ID값 리스트로 반환
    @GetMapping(value = "/images/{boardId}")
    public List<FileDto> getFileList(@PathVariable(value = "boardId") Long boardId) throws SQLException {
        List<FileDto> files = fileService.getFileList(boardId); // 해당 게시글에 첨부된 파일 리스트 반환
        logger.debug("해당 게시물의 이미지의 개수는 {}개 입니다.", files.size());
        return files;
    }

    // 이미지 리턴
    @GetMapping(value = "/image/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> getViewImage(@PathVariable(value = "imageId") Long imageId) throws Exception {
        FileDto fileDTO = fileService.getFile(imageId); // 이미지 정보를 반환 (단수)
        Resource resource = new FileSystemResource(fileDTO.getPath());
        logger.debug("{} 이미지를 조회합니다.", fileDTO.getId());
        return new ResponseEntity<Resource>(resource, HttpStatus.OK);
    }
}
