package com.bindord.eureka.resourceserver.service.specialist;

import com.bindord.eureka.resourceserver.domain.specialist.Specialist;
import com.bindord.eureka.resourceserver.domain.specialist.dto.SpecialistDto;
import com.bindord.eureka.resourceserver.domain.specialist.dto.SpecialistUpdateDto;
import com.bindord.eureka.resourceserver.generic.BaseService;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface SpecialistService extends BaseService<Specialist, UUID, SpecialistDto, SpecialistUpdateDto> {

    Flux<Specialist> findAllNative();
}
