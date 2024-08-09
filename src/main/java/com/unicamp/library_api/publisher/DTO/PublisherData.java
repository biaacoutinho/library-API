package com.unicamp.library_api.publisher.DTO;

import java.util.List;
import java.util.UUID;

public record PublisherData(
    UUID id,
    String name,
    String email,
    List<String> books
) {}
