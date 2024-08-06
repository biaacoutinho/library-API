package com.unicamp.library_api.reader.DTO;

public record ReaderData(
    String cpf,
    String nome,
    String email,
    String telefone,
    String cep
) {}
