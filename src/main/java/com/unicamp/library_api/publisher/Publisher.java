package com.unicamp.library_api.publisher;

import java.util.List;
import java.util.UUID;

import com.unicamp.library_api.book.Book;
import com.unicamp.library_api.publisher.DTO.PublisherRequestPayload;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "publisher")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL)
    private List<Book> books;

    public Publisher(PublisherRequestPayload payload)
    {
        this.name = payload.name();
        this.email = payload.email();
        this.books = null;
    }
}
