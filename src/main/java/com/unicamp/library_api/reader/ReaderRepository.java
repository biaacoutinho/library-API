package com.unicamp.library_api.reader;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReaderRepository extends JpaRepository<Reader, String>
{
    Optional<Reader> findByCpf(String cpf);
}
