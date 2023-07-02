package com.bindord.jaipro.resourceserver.repository;

import com.bindord.jaipro.resourceserver.domain.specialist.SpecialistCv;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecializationResultDto;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface SpecialistCvRepository extends ReactiveCrudRepository<SpecialistCv, UUID> {

    @Query(value = "SELECT * FROM jaipro.get_specializations_by_specialist(:#{[0]})")
    Flux<SpecializationResultDto> getSpecializationsBySpecialistId(UUID specialistId);
}