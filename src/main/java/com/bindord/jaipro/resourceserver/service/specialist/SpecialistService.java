package com.bindord.jaipro.resourceserver.service.specialist;

import com.bindord.jaipro.resourceserver.domain.base.BasePaginateResponse;
import com.bindord.jaipro.resourceserver.domain.json.Rating;
import com.bindord.jaipro.resourceserver.domain.specialist.Specialist;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistDto;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistFiltersSearchDto;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistPublicInformationDto;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistResultSearchDTO;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistUpdateDto;
import com.bindord.jaipro.resourceserver.generic.BaseService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface SpecialistService extends BaseService<Specialist, UUID, SpecialistDto, SpecialistUpdateDto> {

    Flux<Specialist> findAllNative();

    Mono<Boolean> existsSpecialistByDocument(String document);

    Mono<Specialist> updatePresentation(UUID id, SpecialistUpdateDto specialistUpdateDto);

    Mono<SpecialistPublicInformationDto> getPublicInformation(UUID id);

    Mono<BasePaginateResponse<SpecialistResultSearchDTO>> searchSpecialist(SpecialistFiltersSearchDto filters);

    Flux<Rating> getRatings(UUID id);
}