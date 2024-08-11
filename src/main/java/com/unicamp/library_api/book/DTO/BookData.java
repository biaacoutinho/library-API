package com.unicamp.library_api.book.DTO;

import java.time.LocalDate;
import java.util.UUID;

public record BookData(
    UUID id,
    String isbn,
    String title,
    String location,
    String language,
    LocalDate publicationDate,
    String borrowed,
    String publisher_name,
    String author_name
) {}
