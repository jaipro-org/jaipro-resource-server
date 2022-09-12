package com.bindord.eureka.resourceserver.service.country;

import com.bindord.eureka.resourceserver.domain.country.Country;
import com.bindord.eureka.resourceserver.domain.country.dto.CountryDto;
import com.bindord.eureka.resourceserver.domain.country.dto.CountryUpdateDto;
import com.bindord.eureka.resourceserver.generic.BaseService;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CountryService extends BaseService<Country, String, CountryDto, CountryUpdateDto> {

    Flux<Country> findAllNative();
}
