package com.nullpointerexception.nullpointerexception.restapi.service;

import com.nullpointerexception.nullpointerexception.restapi.model.Comment;
import com.nullpointerexception.nullpointerexception.restapi.model.Post;
import com.nullpointerexception.nullpointerexception.restapi.repository.CommentRepository;
import com.nullpointerexception.nullpointerexception.restapi.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private static final int PAGE_SIZE = 3;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    public List<Post> getPosts(int page, Sort.Direction sort) {
        return postRepository.findAllPosts(PageRequest.of(page, PAGE_SIZE, Sort.by(sort, "id"))); //zakres postow zostal ograniczony za pomoca pageRequest (to jest tzw. pagnicaja lub stronicotwanie)
    }

    public Post getSinglePost(Long id) {
        return postRepository.findById(id).get();
    }

    public List<Post> getPostsWithComments(int page, Sort.Direction sort) {
        List<Post> allPosts = postRepository.findAllPosts(PageRequest.of(page, PAGE_SIZE, Sort.by(sort, "id")));
        List<Long> postIds = allPosts.stream() //pobieramy liste id wszystkich postow
                .map(Post::getId) //zamienilismy lambde na metod reference
                .collect(Collectors.toList());
        List<Comment> comments = commentRepository.findAllByPostIdIn(postIds);//pobieramy komentarze na podstawie ids postow
        allPosts.forEach(post -> post.setCommentList(extractComment(comments, post.getId())));  //podlaczamy komentarze do postow
        return allPosts;
    }

    private List<Comment> extractComment(List<Comment> comments, Long id) {
        return comments.stream()
                .filter(comment -> comment.getPostId() == id)
                .collect(Collectors.toList());
    }

    public Post addPost(Post post) {
        return postRepository.save(post);
    }

    @Transactional //dodajemy ta adnotacje poniewaz w tej metodzie mamy dwie transacke bazodanowe poprzez klase postRepository
    public Post editPost(Post post) {
        Post editedPost = postRepository.findById(post.getId()).orElseThrow();
        editedPost.setTitle(post.getTitle());
        editedPost.setContent(post.getContent());
        editedPost.setCreated(post.getCreated());
        return postRepository.save(editedPost);
    }

    public void deletedPost(Long id) {
        postRepository.deleteById(id);
    }
}
