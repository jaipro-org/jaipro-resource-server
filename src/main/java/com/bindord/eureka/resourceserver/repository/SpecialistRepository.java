package com.bindord.eureka.resourceserver.repository;

import com.bindord.eureka.resourceserver.domain.specialist.Specialist;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpecialistRepository extends ReactiveCrudRepository<Specialist, UUID> {

}
