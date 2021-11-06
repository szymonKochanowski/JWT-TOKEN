package com.nullpointerexception.nullpointerexception.restapi.controller;

import com.nullpointerexception.nullpointerexception.restapi.dto.PostDto;
import com.nullpointerexception.nullpointerexception.restapi.dto.PostDtoMapper;
import com.nullpointerexception.nullpointerexception.restapi.model.Post;
import com.nullpointerexception.nullpointerexception.restapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getPosts(@RequestParam(required = false) int page, Sort.Direction sort) { //zamiast post stworzylismy nowy obiekt (klase) PostDto aby zmienijszyc liczbe zapytan sql //dodalismy tez sortowanie albo ASC (rosnaco) albo DESC (malejaco)
        int pageNumber = page >= 0 ? page : 0; // ? to jest operator warunkowy, ktory dziala jak if w tym przypadku jezeli strona jest wieksza lub rowna 0 to zacznij od tej strony w innym przypadku zacznij od strony 0
        return ResponseEntity.ok(PostDtoMapper.mapToPostDtos(postService.getPosts(pageNumber, sort))); //zwracamy liste obiektow //uzylismy klasy PostDtoMapper do wykorystania metody mapujacych klase postow na PostDto
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<Post> getSinglePost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getSinglePost(id));
    }

    @GetMapping("/posts/comments")
    public ResponseEntity<List<Post>> getPostsWithComments(@RequestParam(required = false) int page, @RequestParam (required = false) Sort.Direction sort) {
        int pageNumber = page >= 0 ? page : 0;
        return ResponseEntity.ok(postService.getPostsWithComments(pageNumber, sort));
    }
}
