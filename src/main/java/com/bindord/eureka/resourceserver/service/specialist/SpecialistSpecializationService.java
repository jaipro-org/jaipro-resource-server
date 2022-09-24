package com.bindord.eureka.resourceserver.service.specialist;

import com.bindord.eureka.resourceserver.domain.specialist.SpecialistSpecialization;
import com.bindord.eureka.resourceserver.domain.specialist.dto.SpecialistSpecializationDto;
import com.bindord.eureka.resourceserver.generic.BaseService;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface SpecialistSpecializationService extends BaseService<SpecialistSpecialization, UUID, SpecialistSpecializationDto, SpecialistSpecializationDto> {

    Flux<SpecialistSpecialization> findAllNative();

    Flux<SpecialistSpecialization> findAllBySpecialistId(UUID id);

    Flux<SpecialistSpecialization> saveAll(Iterable<SpecialistSpecialization> workLocations);
}
