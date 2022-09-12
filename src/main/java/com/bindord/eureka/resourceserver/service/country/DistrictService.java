package com.bindord.eureka.resourceserver.service.country;

import com.bindord.eureka.resourceserver.domain.country.District;
import com.bindord.eureka.resourceserver.domain.country.dto.DistrictDto;
import com.bindord.eureka.resourceserver.domain.country.dto.DistrictUpdateDto;
import com.bindord.eureka.resourceserver.generic.BaseService;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface DistrictService extends BaseService<District, Integer, DistrictDto, DistrictUpdateDto> {

    Flux<District> findAllNative();
}
