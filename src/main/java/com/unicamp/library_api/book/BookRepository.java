package com.unicamp.library_api.book;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository <Book, String> {
    Optional<Book> findByIsbn(String isbn);
}
