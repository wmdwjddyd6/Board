package com.min.board.repository.mapper;

import com.min.board.model.Comment;
import com.min.board.paging.Pagination;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    // 댓글 개수 (댓글 관리)
    public int countJoinComment(Pagination pagination) throws Exception;

    // 댓글 리스트 (댓글 관리)
    List<Comment> joinCommentList(Pagination pagination) throws Exception;

    // 댓글 조회
    public List<Comment> selectCommentList(Long boardId) throws Exception;

    // 댓글 작성
    public int insertComment(Comment comment) throws Exception;

    // 댓글 수정
    public int updateComment(Comment comment) throws Exception;

    // 댓글 삭제
    public void deleteComment(Long id) throws Exception;
}
