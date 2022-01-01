package com.min.board.service;

import com.min.board.model.Comment;
import com.min.board.model.Member;
import com.min.board.repository.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class CommentService {

    @Autowired
    private final CommentMapper commentRepository;

    @Autowired
    private BoardService boardService;

    @Autowired
    private MemberService memberService;

    public CommentService(CommentMapper commentMapper) {
        this.commentRepository = commentMapper;
    }

    // 댓글 작성
    public void write(Long boardId, String content, String username) throws Exception {
        Comment comment = new Comment();
        Member member = memberService.getMember(username);

        comment.setBoardId(boardId);
        comment.setContent(content);
        comment.setWriter(member.getUsername());
        comment.setWriterId(member.getId());
        comment.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));

        commentRepository.commentInsert(comment);
    }

}
