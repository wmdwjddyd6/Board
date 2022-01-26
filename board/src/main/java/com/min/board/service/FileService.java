package com.min.board.service;

import com.min.board.model.FileDTO;
import com.min.board.repository.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/*
 *
 * 첨부파일 기능 관련 서비스
 *
 * result : DB 작업이 정상적으로 완료됐는지 체크
 *
 * */
@Service
public class FileService {

    private final FileMapper fileRepository;

    @Autowired
    public FileService(FileMapper fileMapper) {
        this.fileRepository = fileMapper;
    }

    // 게시글 작성 & 수정에서 첨부파일 추가
    public void saveFile(MultipartFile file, Long boardId) throws IOException, SQLException {
        String uuid = UUID.randomUUID().toString(); // 파일명 중복 제거를 위한 uuid 생성
        String originName = file.getOriginalFilename(); // 파일 원본명
        Long fileSize = file.getSize() / 1024; // fileSize를 kb단위로 저장

        String path = "C:/Temp/"; // 서버에 파일 저장 경로
        String newName = ""; // uuid를 포함한 새로운 파일명 (실제 파일 저장 이름)

        if (originName.lastIndexOf(".") < 0) {
            newName = uuid + originName; // 확장자명이 없을 때
        } else {
            newName = uuid + StringUtils.substring(originName, originName.lastIndexOf(".")); //확장자명 포함
        }

        FileDTO fileDTO = new FileDTO(boardId, originName, newName, fileSize, path + newName);

        int result = fileRepository.insertFile(fileDTO);

        if (result > 0 && !file.getOriginalFilename().isEmpty()) {
            file.transferTo(new File(path + newName)); // 서버의 path경로에 newName으로 파일 저장
        }
    }

    // 해당 게시글에 첨부된 파일 목록 반환
    public List<FileDTO> getFileList(Long boardId) throws SQLException {
        List<FileDTO> fileList = fileRepository.selectByBoardId(boardId);
        return fileList;
    }

    // 해당 이미지(ImageID)로 FileDTO 반환
    public FileDTO getFile(Long id) throws Exception {
        FileDTO fileDTO = fileRepository.selectByFileId(id);
        return fileDTO;
    }
}
