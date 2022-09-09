package com.nullpointerexception.nullpointerexception.restapi.repository;

import com.nullpointerexception.nullpointerexception.restapi.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("Select p From Post p")
    List<Post> findAllPosts(Pageable page);

}
