package com.min.board.service;

import com.min.board.model.FileDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileServiceTest {

    @Autowired
    FileService fileService;

    @Test
    public void getFileListTest() throws SQLException {
        Long boardId = 86l;
        List<FileDTO> files = fileService.getFileList(boardId);

        for(FileDTO file : files) {
            System.out.println("fileTest : " + file);
        }
    }
}