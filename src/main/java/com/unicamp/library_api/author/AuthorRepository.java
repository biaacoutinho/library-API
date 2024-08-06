package com.unicamp.library_api.author;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository <Author, UUID> {
}
