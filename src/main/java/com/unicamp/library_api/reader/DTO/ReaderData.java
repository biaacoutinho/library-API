package com.unicamp.library_api.reader.DTO;

public record ReaderData(
    String cpf,
    String name,
    String email,
    String phone,
    String cep
) {}
