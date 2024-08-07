package com.unicamp.library_api.reader.DTO;

public record ReaderResponse(
    String cpf,
    String name,
    String email,
    String phone,
    String cep,
    String streetName,
    String neighborhood,
    String city,
    String uf
) {}
