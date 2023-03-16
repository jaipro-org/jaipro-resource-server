package com.bindord.jaipro.resourceserver.repository;

import com.bindord.jaipro.resourceserver.domain.specialist.SpecialistSpecialization;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface SpecialistSpecializationRepository extends ReactiveCrudRepository<SpecialistSpecialization, UUID> {

    Flux<SpecialistSpecialization> findAllByProfessionId(int ProfessionId);
}
