package com.nullpointerexception.nullpointerexception.restapi.service;

import com.nullpointerexception.nullpointerexception.restapi.model.Post;
import com.nullpointerexception.nullpointerexception.restapi.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    public Post getSinglePost(Long id) {
        return postRepository.findById(id).get();
    }
}
