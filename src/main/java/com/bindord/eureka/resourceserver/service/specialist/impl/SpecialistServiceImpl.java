package com.bindord.eureka.resourceserver.service.specialist.impl;

import com.bindord.eureka.resourceserver.advice.CustomValidationException;
import com.bindord.eureka.resourceserver.advice.NotFoundValidationException;
import com.bindord.eureka.resourceserver.domain.specialist.Specialist;
import com.bindord.eureka.resourceserver.domain.specialist.dto.SpecialistDto;
import com.bindord.eureka.resourceserver.domain.specialist.dto.SpecialistUpdateDto;
import com.bindord.eureka.resourceserver.repository.SpecialistRepository;
import com.bindord.eureka.resourceserver.service.specialist.SpecialistService;
import io.r2dbc.postgresql.codec.Json;
import io.r2dbc.spi.Connection;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.bindord.eureka.resourceserver.utils.Utilitarios.convertJSONtoString;
import static com.bindord.eureka.resourceserver.utils.Utilitarios.getNullPropertyNames;

@AllArgsConstructor
@Service
public class SpecialistServiceImpl implements SpecialistService {

    private final SpecialistRepository repository;

    @Override
    public Mono<Specialist> save(SpecialistDto entity) throws NotFoundValidationException, CustomValidationException {
        return repository.save(convertToEntityForNewCase(entity));
    }

    @Override
    public Mono<Specialist> update(SpecialistUpdateDto entity) throws NotFoundValidationException, CustomValidationException {
        Mono<Specialist> qSpecialist = repository.findById(entity.getId());
        return qSpecialist.flatMap(qCus -> repository.save(convertToEntity(entity, qCus)));
    }

    @Override
    public Mono<Specialist> findOne(UUID id) throws NotFoundValidationException {
        return repository.findById(id);
    }

    private <T> Mono<T> close(Connection connection) {
        return Mono.from(connection.close())
                .then(Mono.empty());
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public Mono<Void> delete() {
        return repository.deleteAll();
    }

    @Override
    public Flux<Specialist> findAll() {
        return repository.findAll();
    }

    @Override
    public Flux<Specialist> findAllNative() {
        return repository.findAll();
    }


    private Specialist convertToEntity(SpecialistUpdateDto obj, Specialist specialist) {
        BeanUtils.copyProperties(obj, specialist, getNullPropertyNames(obj));
        specialist.setRatings(
                Json.of(
                        convertJSONtoString(obj.getRatings())
                )
        );
        return specialist;
    }

    private Specialist convertToEntityForNewCase(SpecialistDto obj) {
        var specialist = new Specialist();
        BeanUtils.copyProperties(obj, specialist);
        specialist.setRatings(
                Json.of(
                        convertJSONtoString(obj.getRatings())
                )
        );
        specialist.setNew(true);
        return specialist;
    }
}
