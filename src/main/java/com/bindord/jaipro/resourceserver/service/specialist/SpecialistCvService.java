package com.bindord.jaipro.resourceserver.service.specialist;

import com.bindord.jaipro.resourceserver.domain.json.Photo;
import com.bindord.jaipro.resourceserver.domain.specialist.SpecialistCv;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistCvDto;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistCvUpdateDto;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistExperienceUpdateDto;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistGalleryUpdateDto;
import com.bindord.jaipro.resourceserver.domain.specialist.json.Experience;
import com.bindord.jaipro.resourceserver.generic.BaseService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface SpecialistCvService extends BaseService<SpecialistCv, UUID, SpecialistCvDto, SpecialistCvUpdateDto> {

    Flux<SpecialistCv> findAllNative();

    Mono<Void> updateExperience(UUID id, SpecialistExperienceUpdateDto entity);
    Mono<Experience> saveExperience(UUID id, Experience experience);
    Flux<Photo> updateGallery(SpecialistGalleryUpdateDto entity);
}
