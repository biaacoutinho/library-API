package com.unicamp.library_api.author;

import com.unicamp.library_api.author.DTO.AuthorRequestPayload;
import com.unicamp.library_api.book.Book;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Book> books;

    public Author (AuthorRequestPayload data) {
        this.name = data.name();
        this.books = null;
    }
}