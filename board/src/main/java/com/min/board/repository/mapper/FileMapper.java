package com.min.board.repository.mapper;

import com.min.board.model.FileDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {
    int insertFile(FileDTO fileDTO);
}
