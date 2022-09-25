package com.bindord.eureka.resourceserver.service.specialist;

import com.bindord.eureka.resourceserver.domain.specialist.SpecialistCv;
import com.bindord.eureka.resourceserver.domain.specialist.dto.SpecialistCvDto;
import com.bindord.eureka.resourceserver.domain.specialist.dto.SpecialistCvUpdateDto;
import com.bindord.eureka.resourceserver.generic.BaseService;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface SpecialistCvService extends BaseService<SpecialistCv, UUID, SpecialistCvDto, SpecialistCvUpdateDto> {

    Flux<SpecialistCv> findAllNative();
}
