package com.unicamp.library_api.book;

import com.unicamp.library_api.author.Author;
import com.unicamp.library_api.book.DTO.BookRequestPayload;
import com.unicamp.library_api.loan.Loan;
import com.unicamp.library_api.publisher.Publisher;
import com.unicamp.library_api.reader.Reader;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String location;

    @Column(name = "publication_date", nullable = false)
    private LocalDate publicationDate;

    @Column(nullable = false)
    private Boolean borrowed;

    @Column(nullable = false)
    private String language;

    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Loan> loans;

    public Book(BookRequestPayload payload, Author author, Publisher publisher)
    {
        this.isbn = payload.isbn();
        this.title = payload.title();
        this.location = payload.location();
        this.publicationDate = payload.publicationDate();
        this.borrowed = false;
        this.language = payload.language();
        this.publisher = publisher;
        this.author = author;
    }
}