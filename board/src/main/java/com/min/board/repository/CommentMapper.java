package com.min.board.repository;

import com.min.board.model.CommentDto;
import com.min.board.paging.Pagination;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*
 *
 * 댓글 기능 관련 Repository
 * MyBatis 사용 : resources - mapper에 xml을 통해 sql 관리
 *
 * */
@Mapper
public interface CommentMapper {
    // 댓글 개수 (댓글 관리)
    int countJoinComment(Pagination pagination) throws Exception;

    // 댓글 리스트 (댓글 관리)
    List<CommentDto> joinCommentList(Pagination pagination) throws Exception;

    // 댓글 조회
    List<CommentDto> selectCommentList(Long boardId) throws Exception;

    // 댓글 작성
    int insertComment(CommentDto commentDto) throws Exception;

    // 댓글 수정
    int updateComment(CommentDto commentDto) throws Exception;

    // 댓글 삭제
    int deleteComment(Long id) throws Exception;
}
