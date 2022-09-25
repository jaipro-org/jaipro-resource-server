package com.bindord.eureka.resourceserver.repository;

import com.bindord.eureka.resourceserver.domain.specialist.SpecialistCv;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpecialistCvRepository extends ReactiveCrudRepository<SpecialistCv, UUID> {

}
