package com.min.board.repository;

import com.min.board.model.FileDTO;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface FileMapper {
    // 파일 첨부
    int insertFile(FileDTO fileDTO) throws SQLException;

    // BoardId로 파일 리스트 불러오기
    List<FileDTO> selectByBoardId(Long boardId) throws SQLException;

    // ImageID로 FileDTO 반환
    FileDTO selectByFileId(Long id);
}
