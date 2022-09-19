package com.bindord.eureka.resourceserver.service.specialist;

import com.bindord.eureka.resourceserver.domain.specialist.WorkLocation;
import com.bindord.eureka.resourceserver.domain.specialist.dto.WorkLocationDto;
import com.bindord.eureka.resourceserver.generic.BaseService;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface WorkLocationService extends BaseService<WorkLocation, UUID, WorkLocationDto, WorkLocationDto> {

    Flux<WorkLocation> findAllNative();

    Flux<WorkLocation> findAllBySpecialistId(UUID id);

    Flux<WorkLocation> saveAll(Iterable<WorkLocation> workLocations);
}
