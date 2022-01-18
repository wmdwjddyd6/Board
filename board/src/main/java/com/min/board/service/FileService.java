package com.min.board.service;

import com.min.board.model.FileDTO;
import com.min.board.repository.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    private final FileMapper fileRepository;

    @Autowired
    public FileService(FileMapper fileMapper) {
        this.fileRepository = fileMapper;
    }

    // 게시글 작성 & 수정에서 첨부파일 추가
    public void saveFile(MultipartFile file, Long boardId) throws IOException, SQLException {
        String uuid = UUID.randomUUID().toString(); // 파일명 중복 제거를 위한 uuid
        String originName = file.getOriginalFilename(); // 파일 원본명
        Long fileSize = file.getSize() / 1024; // kb

        String path = "C:/Temp/";
        String newName = "";

        if (originName.lastIndexOf(".") < 0) {
            newName = uuid + originName; // 확장자명이 없을 때
        } else {
            newName = uuid + StringUtils.substring(originName, originName.lastIndexOf(".")); //확장자명 포함
        }

        FileDTO fileDTO = new FileDTO(boardId, originName, newName, fileSize, path + newName);

        int result = fileRepository.insertFile(fileDTO);

        if (result > 0 && !file.getOriginalFilename().isEmpty()) {
            file.transferTo(new File(path + newName));
        }
    }

    // BoardId로 File리스트 반환
    public List<FileDTO> getFileList(Long boardId) throws SQLException {
        List<FileDTO> fileList = fileRepository.selectByBoardId(boardId);
        return fileList;
    }

    // ImageID로 FileDTO 반환
    public FileDTO getFile(Long id) throws SQLException {
        FileDTO fileDTO = fileRepository.selectByFileId(id);
        return fileDTO;
    }
}
