package com.bindord.jaipro.resourceserver.service.specialist;

import com.bindord.jaipro.resourceserver.domain.specialist.WorkLocation;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.WorkLocationDto;
import com.bindord.jaipro.resourceserver.generic.BaseService;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface WorkLocationService extends BaseService<WorkLocation, UUID, WorkLocationDto, WorkLocationDto> {

    Flux<WorkLocation> findAllNative();

    Flux<WorkLocation> findAllBySpecialistId(UUID id);

    Flux<WorkLocation> saveAll(Iterable<WorkLocation> workLocations);
}
