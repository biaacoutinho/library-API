package com.unicamp.library_api.book;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository <Book, UUID> {
    Optional<Book> findByIsbn(String isbn);
}
