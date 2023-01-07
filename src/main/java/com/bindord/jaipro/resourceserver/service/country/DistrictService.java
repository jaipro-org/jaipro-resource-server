package com.bindord.jaipro.resourceserver.service.country;

import com.bindord.jaipro.resourceserver.domain.country.District;
import com.bindord.jaipro.resourceserver.domain.country.dto.DistrictDto;
import com.bindord.jaipro.resourceserver.domain.country.dto.DistrictUpdateDto;
import com.bindord.jaipro.resourceserver.generic.BaseService;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface DistrictService extends BaseService<District, Integer, DistrictDto, DistrictUpdateDto> {

    Flux<District> findAllNative();
}
