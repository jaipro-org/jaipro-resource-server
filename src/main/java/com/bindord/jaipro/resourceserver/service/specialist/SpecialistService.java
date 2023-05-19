package com.bindord.jaipro.resourceserver.service.specialist;

import com.bindord.jaipro.resourceserver.advice.CustomValidationException;
import com.bindord.jaipro.resourceserver.advice.NotFoundValidationException;
import com.bindord.jaipro.resourceserver.domain.specialist.Specialist;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistDto;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistUpdateDto;
import com.bindord.jaipro.resourceserver.generic.BaseService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface SpecialistService extends BaseService<Specialist, UUID, SpecialistDto, SpecialistUpdateDto> {

    Flux<Specialist> findAllNative();

    Mono<Boolean> existsSpecialistByDocument(String document);

    Mono<Specialist> updatePresentation(UUID id, SpecialistUpdateDto specialistUpdateDto);
}
