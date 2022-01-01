package com.min.board.repository.mapper;

import com.min.board.model.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    // 댓글 개수
    public int countComment() throws Exception;

    // 댓글 리스트
    public List<Comment> selectCommentList(Long boardId) throws Exception;

    // 댓글 작성
    public int insertComment(Comment comment) throws Exception;

    // 댓글 수정
    public int updateComment(Comment comment) throws Exception;

    // 댓글 삭제
    public int deleteComment(int id) throws Exception;
}
