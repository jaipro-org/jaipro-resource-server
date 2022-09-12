package com.bindord.eureka.resourceserver.service.country.impl;

import com.bindord.eureka.resourceserver.advice.CustomValidationException;
import com.bindord.eureka.resourceserver.advice.NotFoundValidationException;
import com.bindord.eureka.resourceserver.domain.country.Country;
import com.bindord.eureka.resourceserver.domain.country.dto.CountryDto;
import com.bindord.eureka.resourceserver.domain.country.dto.CountryUpdateDto;
import com.bindord.eureka.resourceserver.repository.CountryRepository;
import com.bindord.eureka.resourceserver.service.country.CountryService;
import io.r2dbc.spi.Connection;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.bindord.eureka.resourceserver.utils.Utilitarios.getNullPropertyNames;

@AllArgsConstructor
@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository repository;

    @Override
    public Mono<Country> save(CountryDto entity) throws NotFoundValidationException, CustomValidationException {
        return repository.save(convertToEntityForNewCase(entity));
    }

    @Override
    public Mono<Country> update(CountryUpdateDto entity) throws NotFoundValidationException, CustomValidationException {
        Mono<Country> qCountry = repository.findById(entity.getId());
        return qCountry.flatMap(qCus -> repository.save(convertToEntity(entity, qCus)));
    }

    @Override
    public Mono<Country> findOne(String id) throws NotFoundValidationException {
        return repository.findById(id);
    }

    private <T> Mono<T> close(Connection connection) {
        return Mono.from(connection.close())
                .then(Mono.empty());
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public Mono<Void> delete() {
        return repository.deleteAll();
    }

    @Override
    public Flux<Country> findAll() {
        return repository.findAll();
    }

    @Override
    public Flux<Country> findAllNative() {
        return repository.findAll();
    }


    private Country convertToEntity(CountryUpdateDto obj, Country country) {
        BeanUtils.copyProperties(obj, country, getNullPropertyNames(obj));
        return country;
    }

    private Country convertToEntityForNewCase(CountryDto obj) {
        var country = new Country();
        BeanUtils.copyProperties(obj, country);
        country.setNew(true);
        return country;
    }
}
