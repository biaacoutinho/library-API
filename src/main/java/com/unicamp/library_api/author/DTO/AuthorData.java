package com.unicamp.library_api.author.DTO;

import java.util.List;
import java.util.UUID;

public record AuthorData(
        UUID id,
        String name,
        List<String> books
) {}
