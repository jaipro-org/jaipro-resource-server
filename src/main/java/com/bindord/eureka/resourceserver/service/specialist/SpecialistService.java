package com.bindord.eureka.resourceserver.service.specialist;

import com.bindord.eureka.resourceserver.domain.specialist.Specialist;
import com.bindord.eureka.resourceserver.domain.specialist.dto.SpecialistDto;
import com.bindord.eureka.resourceserver.domain.specialist.dto.SpecialistUpdateDto;
import com.bindord.eureka.resourceserver.generic.BaseService;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface SpecialistService extends BaseService<Specialist, UUID, SpecialistDto, SpecialistUpdateDto> {

    Flux<Specialist> findAllNative();
}
