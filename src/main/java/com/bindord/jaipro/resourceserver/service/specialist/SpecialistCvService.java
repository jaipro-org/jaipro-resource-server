package com.bindord.jaipro.resourceserver.service.specialist;

import com.bindord.jaipro.resourceserver.domain.specialist.SpecialistCv;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistCvDto;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistCvUpdateDto;
import com.bindord.jaipro.resourceserver.generic.BaseService;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface SpecialistCvService extends BaseService<SpecialistCv, UUID, SpecialistCvDto, SpecialistCvUpdateDto> {

    Flux<SpecialistCv> findAllNative();
}
