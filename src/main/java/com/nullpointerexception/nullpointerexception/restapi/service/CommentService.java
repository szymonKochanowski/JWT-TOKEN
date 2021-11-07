package com.nullpointerexception.nullpointerexception.restapi.service;

import com.nullpointerexception.nullpointerexception.restapi.model.Comment;
import com.nullpointerexception.nullpointerexception.restapi.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CommentService {

    private static final int PAGE_SIZE = 4;

    @Autowired
    CommentRepository commentRepository;

    public List<Comment> findAllComments(Integer page, Sort.Direction sort) {
        return commentRepository.findAllComments(PageRequest.of(page, PAGE_SIZE, Sort.by(sort, "id")));
    }

    public Comment findCommentById(Long id) {
        return commentRepository.findById(id).orElseThrow();
    }

    public Comment addNewComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Transactional
    public Comment editComment(Comment comment) {
        Comment editedComment = commentRepository.findById(comment.getId()).orElseThrow();
        editedComment.setId(comment.getId());
        editedComment.setContent(comment.getContent());
        editedComment.setCreated(comment.getCreated());
        return commentRepository.save(editedComment);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
