package com.unicamp.library_api.author.DTO;

import java.util.UUID;

public record AuthorCreateResponse(
    UUID id,
    String name
) 
{}
