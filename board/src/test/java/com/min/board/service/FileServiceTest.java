package com.min.board.service;

import com.min.board.model.FileDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.List;

@SpringBootTest
class FileServiceTest {

    @Autowired
    FileService fileService;

    @Test
    public void getFileListTest() throws SQLException {
        Long boardId = 87l;
        List<FileDto> files = fileService.getFileList(boardId);

        for(FileDto file : files) {
            System.out.println("fileTest : " + file);
        }
    }
}