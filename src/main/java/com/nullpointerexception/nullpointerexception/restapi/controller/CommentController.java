package com.nullpointerexception.nullpointerexception.restapi.controller;

import com.nullpointerexception.nullpointerexception.restapi.model.Comment;
import com.nullpointerexception.nullpointerexception.restapi.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@Slf4j
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("/comments")
    public ResponseEntity<List<Comment>> getAllComments(@RequestParam(required = false) Integer page, Sort.Direction sort) {
        Integer pageNumber = page != null && page > 0 ? page : 0;
        Sort.Direction wayOfSort = sort != null ? sort : Sort.Direction.ASC;
        log.info("Start to get all commetns.");
        return ResponseEntity.ok(commentService.findAllComments(pageNumber, wayOfSort));
    }

    @GetMapping("/comment/{id}")
    public ResponseEntity<Comment> getSingleComment(@PathVariable Long id) {
        log.info("Start to get commetn with id: " + id + " .");
        return ResponseEntity.ok(commentService.findCommentById(id));
    }

    @PostMapping("/comment/add")
    public ResponseEntity<Comment> addNewComment(@RequestBody Comment comment) {
        log.info("Start to add new comment.");
        return ResponseEntity.ok(commentService.addNewComment(comment));
    }

    @PutMapping("/comment/edit")
    public ResponseEntity<Comment> editComment(@RequestBody Comment comment) {
        log.info("Start to edit comment.");
        return ResponseEntity.ok(commentService.editComment(comment));
    }

    @DeleteMapping("/comment/deleted/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        log.info("Start to delete comment with id: " + id);
        commentService.deleteComment(id);
        log.info("Deleted comment with id: " + id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
