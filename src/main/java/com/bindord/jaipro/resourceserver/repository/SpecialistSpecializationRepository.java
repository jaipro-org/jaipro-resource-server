package com.bindord.jaipro.resourceserver.repository;

import com.bindord.jaipro.resourceserver.domain.specialist.SpecialistSpecialization;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpecialistSpecializationRepository extends ReactiveCrudRepository<SpecialistSpecialization, UUID> {

}
