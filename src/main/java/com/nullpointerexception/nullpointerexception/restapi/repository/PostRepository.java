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

//    @Query("select p from Post p where title = :title") //w Jpa nie odwolujemy sie do tabel ale do obiektow czyli zazwyczaj do encji //query uzywany najczesniej kiedy chcemy zostowoac joiny czyli zapytanie odwolujace sie do roznych tabel
//    List<Post> findAllByTitle(@Param("title") String title);

    //@Query("Select p From Post p" + " left join fetch p.commentList") //pobieramy wszystkie komentarze wraz z postami -wykorzystujemy pole relacji //dzieki temu zamiast 4 roznych zapytan sql mamy tylko 1
    @Query("Select p From Post p")
    List<Post> findAllPosts(Pageable page); //Pageable umozliwia stronicowanie

}
