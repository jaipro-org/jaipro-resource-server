package com.bindord.jaipro.resourceserver.repository;

import com.bindord.jaipro.resourceserver.domain.specialist.SpecialistSpecialization;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpecialistSpecializationRepository extends ReactiveCrudRepository<SpecialistSpecialization, UUID> {

    Flux<SpecialistSpecialization> findAllByProfessionId(int ProfessionId);

    Mono<Void> deleteBySpecialistIdAndProfessionId(UUID id, Integer professionId);

    Mono<Void> deleteBySpecialistIdAndSpecializationIdIn(UUID id, List<Integer> specializationIds);
}
