package com.unicamp.library_api.publisher;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, UUID> {

}
