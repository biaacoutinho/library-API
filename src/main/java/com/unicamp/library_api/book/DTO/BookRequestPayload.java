package com.unicamp.library_api.book.DTO;

import java.time.LocalDate;
import java.util.UUID;

public record BookRequestPayload(
    String isbn,
    String title,
    String location,
    LocalDate publicationDate,
    String language,
    UUID publisherId,
    UUID authorId
) {}
