package com.unicamp.library_api.book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicamp.library_api.Response;
import com.unicamp.library_api.author.Author;
import com.unicamp.library_api.author.AuthorRepository;
import com.unicamp.library_api.book.DTO.BookData;
import com.unicamp.library_api.book.DTO.BookRequestPayload;
import com.unicamp.library_api.publisher.Publisher;
import com.unicamp.library_api.publisher.PublisherRepository;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookRepository repository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @GetMapping
    public List<BookData> getAll()
    {
        List<Book> books = this.repository.findAll();

        List<BookData> list = books.stream().map(book -> new BookData(book.getId(), book.getIsbn(), book.getTitle(), book.getLocation(), book.getLanguage(), book.getPublicationDate(), book.getBorrowed()? "Borrowed" : "Available", book.getAuthor().getName(), book.getPublisher().getName())).toList();
        
        return list;
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable UUID id) {
        Optional<Book> book = this.repository.findById(id);

        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn) {
        Optional<Book> book = this.repository.findByIsbn(isbn);

        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Response> create(@RequestBody BookRequestPayload payload) {   

        Optional<Author> optionalAuthor = authorRepository.findById(payload.authorId());
        Optional<Publisher> optionalPublisher = publisherRepository.findById(payload.publisherId());

        if (!optionalAuthor.isPresent())
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new Response("Could not find author with specified id", null));

        if (!optionalPublisher.isPresent())
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new Response("Could not find publisher with specified id", null));

        Author author = optionalAuthor.get();
        Publisher publisher = optionalPublisher.get();

        Book newBook = new Book(payload, author, publisher);

        Book created = this.repository.save(newBook);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new Response(
                "Successfully created book",
                new BookData(created.getId(), created.getIsbn(), created.getTitle(), created.getLocation(), created.getLanguage(), created.getPublicationDate(), created.getBorrowed()? "Borrowed" : "Available", created.getAuthor().getName(), created.getPublisher().getName())
            ));
    }
}
