package com.nullpointerexception.nullpointerexception.restapi.repository;

import com.nullpointerexception.nullpointerexception.restapi.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
