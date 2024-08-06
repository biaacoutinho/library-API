package com.unicamp.library_api.author.DTO;

import java.util.UUID;

public record AuthorResponse(
    UUID id,
    String name
) 
{}
