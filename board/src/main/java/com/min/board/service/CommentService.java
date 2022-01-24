package com.min.board.service;

import com.min.board.model.Comment;
import com.min.board.model.Member;
import com.min.board.paging.Pagination;
import com.min.board.repository.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private final CommentMapper commentRepository;

    @Autowired
    private BoardService boardService;

    @Autowired
    private MemberService memberService;

    @Autowired
    public CommentService(CommentMapper commentMapper) {
        this.commentRepository = commentMapper;
    }

    // 댓글 관리 리스트 (페이징)
    public List<Comment> userCommentList(Pagination pagination) throws Exception{
        return commentRepository.joinCommentList(pagination);
    }

    // 댓글 관리 카운트 (페이징)
    public int getCommentCnt(Pagination pagination) throws Exception {
        return commentRepository.countJoinComment(pagination);
    }

    // 댓글 작성
    public int write(Long boardId, String content, String username) throws Exception {
        Comment comment = new Comment();
        Member member = memberService.getMember(username);

        comment.setBoardId(boardId);
        comment.setContent(content);
        comment.setWriter(member.getUsername());
        comment.setWriterId(member.getId());
        comment.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));

        int result = commentRepository.insertComment(comment);
        return result;
    }

    // 댓글 조회
    public List<Comment> getCommentList(Long boardId) throws Exception {
        return commentRepository.selectCommentList(boardId);
    }

    // 댓글 수정
    public int update(Long commentId, String content) throws Exception {
        Comment comment = new Comment();

        comment.setId(commentId);
        comment.setContent(content);

        int result = commentRepository.updateComment(comment);
        return result;
    }

    // 댓글 삭제
    public int delete(Long commentId) throws Exception {
        int result = commentRepository.deleteComment(commentId);
        return result;
    }
}
