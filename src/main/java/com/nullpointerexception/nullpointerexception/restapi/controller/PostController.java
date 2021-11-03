package com.nullpointerexception.nullpointerexception.restapi.controller;

import com.nullpointerexception.nullpointerexception.restapi.model.Post;
import com.nullpointerexception.nullpointerexception.restapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getPosts() {
        return ResponseEntity.ok(postService.getPosts());
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<Post> getSinglePost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getSinglePost(id));
    }
}
