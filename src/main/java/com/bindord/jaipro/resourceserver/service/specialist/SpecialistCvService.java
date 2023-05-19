package com.bindord.jaipro.resourceserver.service.specialist;

import com.bindord.jaipro.resourceserver.domain.json.Photo;
import com.bindord.jaipro.resourceserver.domain.specialist.SpecialistCv;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistCvDto;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistCvUpdateDto;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistExperienceUpdateDto;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistGalleryUpdateDto;
import com.bindord.jaipro.resourceserver.domain.specialist.json.Experience;
import com.bindord.jaipro.resourceserver.generic.BaseService;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface SpecialistCvService extends BaseService<SpecialistCv, UUID, SpecialistCvDto, SpecialistCvUpdateDto> {

    Flux<SpecialistCv> findAllNative();

    Mono<SpecialistCv> updatePresentation(UUID id, SpecialistCvUpdateDto specialist, String url);
    Mono<Void> updateExperience(UUID id, SpecialistExperienceUpdateDto entity);
    Mono<Experience> saveExperience(UUID id, Experience experience);
    Mono<SpecialistCv> updateGallery(List<FilePart> images, SpecialistGalleryUpdateDto entity);
}
