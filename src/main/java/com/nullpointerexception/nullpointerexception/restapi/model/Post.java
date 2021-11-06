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
    @GeneratedValue(strategy = GenerationType.IDENTITY) //typ indentity ustawia entity operajac sie o kolumne w bazie danych czyli primary key (id w tym przypadku)
    private Long id;
    private String title;
    private String content;
    private LocalDateTime created;

    @OneToMany(cascade = CascadeType.REMOVE) //kazdy post moze miec wiele komentarzy //jezeli chcemy aby hibernate usuwal nam commentarze przypisane do danego posta podczas jego updejtu to wybieramy ocje orphanRemoval = true// dodalismy casecade type remove aby miec mozliwosc usuniecia danego posta
    @JoinColumn(name = "postId", updatable = false, insertable = false) //mapujemy kolumne na ktorej jest powiazanie z klasa Comment //update table oraz insertable pozwala na zachownie komentarzy podczas edyci posta
    private List<Comment> commentList;
}
