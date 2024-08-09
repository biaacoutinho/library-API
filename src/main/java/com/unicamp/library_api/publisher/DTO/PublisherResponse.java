package com.unicamp.library_api.publisher.DTO;

import java.util.UUID;

public record PublisherResponse(
    UUID id,
    String name,
    String email
) {}
