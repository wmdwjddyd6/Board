package com.min.board.repository.mapper;

import com.min.board.model.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    // 댓글 개수
    public int commentCount() throws Exception;

    // 댓글 목록
    public List<Comment> commentList() throws Exception;

    // 댓글 작성
    public int commentInsert(Comment comment) throws Exception;

    // 댓글 수정
    public int commentUpdate(Comment comment) throws Exception;

    // 댓글 삭제
    public int commentDelete(int id) throws Exception;
}
