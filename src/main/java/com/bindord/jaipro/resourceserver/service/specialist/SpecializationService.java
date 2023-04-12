package com.bindord.jaipro.resourceserver.service.specialist;

import com.bindord.jaipro.resourceserver.domain.specialist.Specialization;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecializationDto;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecializationUpdateDto;
import com.bindord.jaipro.resourceserver.generic.BaseService;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface SpecializationService extends BaseService<Specialization, Integer, SpecializationDto, SpecializationUpdateDto> {

    Flux<Specialization> findAllNative();
}
