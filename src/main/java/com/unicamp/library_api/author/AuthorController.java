package com.unicamp.library_api.author;

import com.unicamp.library_api.Response;
import com.unicamp.library_api.author.DTO.AuthorCreateResponse;
import com.unicamp.library_api.author.DTO.AuthorData;
import com.unicamp.library_api.author.DTO.AuthorRequestPayload;
import com.unicamp.library_api.book.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
        System.out.println(created.toString());
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new Response(
                "Successfully created author",
               new AuthorCreateResponse(created.getId(), created.getName())
            ));
    }
}
