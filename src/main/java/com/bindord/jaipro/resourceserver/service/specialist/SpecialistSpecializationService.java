package com.bindord.jaipro.resourceserver.service.specialist;

import com.bindord.jaipro.resourceserver.domain.specialist.SpecialistSpecialization;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistSpecializationDto;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistSpecializationUpdateDto;
import com.bindord.jaipro.resourceserver.generic.BaseService;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface SpecialistSpecializationService extends BaseService<SpecialistSpecialization, UUID, SpecialistSpecializationDto, SpecialistSpecializationUpdateDto> {

    Flux<SpecialistSpecialization> findAllNative();

    Flux<SpecialistSpecialization> findAllBySpecialistId(UUID id);

    Flux<SpecialistSpecialization> saveAll(Iterable<SpecialistSpecialization> workLocations);
}
