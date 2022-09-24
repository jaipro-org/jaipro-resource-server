package com.bindord.eureka.resourceserver.repository;

import com.bindord.eureka.resourceserver.domain.specialist.SpecialistSpecialization;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpecialistSpecializationRepository extends ReactiveCrudRepository<SpecialistSpecialization, UUID> {

}
