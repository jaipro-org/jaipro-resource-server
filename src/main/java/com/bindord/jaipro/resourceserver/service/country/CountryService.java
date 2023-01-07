package com.bindord.jaipro.resourceserver.service.country;

import com.bindord.jaipro.resourceserver.domain.country.Country;
import com.bindord.jaipro.resourceserver.domain.country.dto.CountryDto;
import com.bindord.jaipro.resourceserver.domain.country.dto.CountryUpdateDto;
import com.bindord.jaipro.resourceserver.generic.BaseService;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CountryService extends BaseService<Country, String, CountryDto, CountryUpdateDto> {

    Flux<Country> findAllNative();
}
