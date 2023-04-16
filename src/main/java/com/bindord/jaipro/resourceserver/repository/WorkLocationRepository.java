package com.bindord.jaipro.resourceserver.repository;

import com.bindord.jaipro.resourceserver.domain.specialist.WorkLocation;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface WorkLocationRepository extends ReactiveCrudRepository<WorkLocation, UUID> {
    Mono<Void> deleteBySpecialistIdAndDistrictId(UUID specialistId, int districtID);
}
