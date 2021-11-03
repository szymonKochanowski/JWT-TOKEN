package com.nullpointerexception.nullpointerexception.restapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String content;
    private LocalDateTime created;

    @OneToMany //kazdy post moze miec wiele komentarzy
    @JoinColumn(name = "post_id") //mapujemy kolumne na ktorej jest powiazanie z klasa Comment
    private List<Comment> commentList;
}
