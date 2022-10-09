package com.nullpointerexception.nullpointerexception.restapi.controller;

import com.nullpointerexception.nullpointerexception.restapi.dto.PostDto;
import com.nullpointerexception.nullpointerexception.restapi.dto.PostDtoMapper;
import com.nullpointerexception.nullpointerexception.restapi.model.Post;
import com.nullpointerexception.nullpointerexception.restapi.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getPosts(@RequestParam(required = false) Integer page, @RequestParam(required = false) Sort.Direction sort) {
        int pageNumber = page != null && page > 0 ? page : 0;
        Sort.Direction sortDirection = sort != null ? sort : Sort.Direction.ASC;
        log.info("Start to get all posts");
        return ResponseEntity.ok(PostDtoMapper.mapToPostDtos(postService.getPosts(pageNumber, sortDirection)));
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<Post> getSinglePost(@PathVariable Long id) {
        log.info("Start to get post with id: " + id);
        return ResponseEntity.ok(postService.getSinglePost(id));
    }

    @GetMapping("/posts/comments")
    public ResponseEntity<List<Post>> getPostsWithComments(@RequestParam(required = false) Integer page, @RequestParam (required = false) Sort.Direction sort) {
        int pageNumber = page != null && page > 0 ? page : 0;
        Sort.Direction sortDirection = sort != null ? sort : Sort.Direction.ASC;
        log.info("Start to get all posts with comments");
        return ResponseEntity.ok(postService.getPostsWithComments(pageNumber, sortDirection));
    }

    @PostMapping("/post/add")
    public ResponseEntity<Post> addPost(@RequestBody Post post) {
        log.info("Start to add new post");
        return ResponseEntity.ok(postService.addPost(post));
    }

    @PutMapping("/post/edit")
    public ResponseEntity<Post> editPost(@RequestBody Post post) {
        log.info("Start edit post");
        return ResponseEntity.ok(postService.editPost(post));
    }

    @DeleteMapping("/post/deleted/{id}")
    public ResponseEntity<Void> deletedPost(@PathVariable Long id) {
        log.info("Start deleted post with id: " + id);
        postService.deletedPost(id);
        log.info("Deleted post with id: " + id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
