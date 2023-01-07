package com.bindord.jaipro.resourceserver.repository;

import com.bindord.jaipro.resourceserver.domain.specialist.Specialist;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface SpecialistRepository extends ReactiveCrudRepository<Specialist, UUID> {

    Mono<Boolean> existsSpecialistByDocument(String document);
}
