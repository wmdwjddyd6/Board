package com.min.board.repository.mapper;

import com.min.board.model.Comment;

import java.util.List;

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
    public int commentDelete(int cno) throws Exception;
}
