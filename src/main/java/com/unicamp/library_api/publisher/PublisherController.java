package com.unicamp.library_api.publisher;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicamp.library_api.Response;
import com.unicamp.library_api.book.Book;
import com.unicamp.library_api.publisher.DTO.PublisherData;
import com.unicamp.library_api.publisher.DTO.PublisherRequestPayload;
import com.unicamp.library_api.publisher.DTO.PublisherResponse;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/publishers")
public class PublisherController {
    @Autowired
    private PublisherRepository repository;

    @GetMapping
    public List<PublisherData> getAll()
    {
        List<Publisher> publishers = this.repository.findAll();

        List<PublisherData> list = publishers.stream().map(publisher -> new PublisherData(publisher.getId(), publisher.getName(), publisher.getEmail(), publisher.getBooks() != null? publisher.getBooks().stream().map(Book::getTitle).collect(Collectors.toList()) : null)).toList();
        
        return list;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getPublisher(@PathVariable UUID id) {
        Optional<Publisher> publisher = this.repository.findById(id);

        return publisher.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Response> create(@RequestBody PublisherRequestPayload payload)
    {
        Publisher newPublisher = new Publisher(payload);

        Publisher created = this.repository.save(newPublisher);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new Response(
                "Successfully created publisher",
               new PublisherResponse(created.getId(), created.getName(), created.getEmail())
            ));
    }  

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") UUID id) {
        Publisher exists = this.repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Could not find publisher with specified id"));

        this.repository.deleteById(id);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new Response("Successfully deleted publisher with specified id", null));
    }
}
