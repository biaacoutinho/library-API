package com.unicamp.library_api.author;

import com.unicamp.library_api.Response;
import com.unicamp.library_api.author.DTO.AuthorResponse;
import com.unicamp.library_api.author.DTO.AuthorData;
import com.unicamp.library_api.author.DTO.AuthorRequestPayload;
import com.unicamp.library_api.book.Book;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping
    public List<AuthorData> getAll()
    {
        List<Author> authors = this.authorRepository.findAll();

        List<AuthorData> list = authors.stream().map(author -> new AuthorData(author.getId(), author.getName(), author.getBooks() != null? author.getBooks().stream().map(Book::getTitle).collect(Collectors.toList()) : null)).toList();
        
        return list;
    }

    @PostMapping
    public ResponseEntity<Response> createAuthor(@RequestBody AuthorRequestPayload payload)
    {
        Author newAuthor = new Author(payload);

        Author created = this.authorRepository.save(newAuthor);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new Response(
                "Successfully created author",
               new AuthorResponse(created.getId(), created.getName())
            ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateAuthor(@PathVariable UUID id, @RequestBody AuthorRequestPayload payload) 
    {
        Author current = this.authorRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Could not fetch author with specified id"));
            
        Author newAuthor = new Author(current.getId(), payload.name(), current.getBooks());

        Author updated = this.authorRepository.save(newAuthor);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new Response(
                "Successfully updated author",
                new AuthorResponse(updated.getId(), updated.getName())
        ));
    }
}
