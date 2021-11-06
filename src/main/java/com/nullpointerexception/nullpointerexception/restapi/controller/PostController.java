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
    public ResponseEntity<List<PostDto>> getPosts(@RequestParam(required = false) Integer page, @RequestParam(required = false) Sort.Direction sort) { //zamiast post stworzylismy nowy obiekt (klase) PostDto aby zmienijszyc liczbe zapytan sql //dodalismy tez sortowanie albo ASC (rosnaco) albo DESC (malejaco)
        int pageNumber = page != null && page > 0 ? page : 0; // ? to jest operator warunkowy, ktory dziala jak if w tym przypadku jezeli strona jest wieksza lub rowna 0 to zacznij od tej strony w innym przypadku zacznij od strony 0
        Sort.Direction sortDirection = sort != null ? sort : Sort.Direction.ASC; //podobnie jak wyzje tylko jezeli sort nie jest nullem to wybierz sort w innym przypadku domysle ustawiamy na rosnaco
        log.info("Start to get all posts");
        return ResponseEntity.ok(PostDtoMapper.mapToPostDtos(postService.getPosts(pageNumber, sortDirection))); //zwracamy liste obiektow //uzylismy klasy PostDtoMapper do wykorystania metody mapujacych klase postow na PostDto
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
         postService.deletedPost(id);
         return new ResponseEntity(HttpStatus.OK);
    }
}
