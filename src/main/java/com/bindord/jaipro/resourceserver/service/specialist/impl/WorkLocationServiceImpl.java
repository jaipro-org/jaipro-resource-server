package com.bindord.jaipro.resourceserver.service.specialist.impl;

import com.bindord.jaipro.resourceserver.advice.CustomValidationException;
import com.bindord.jaipro.resourceserver.advice.NotFoundValidationException;
import com.bindord.jaipro.resourceserver.domain.specialist.WorkLocation;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.WorkLocationDto;
import com.bindord.jaipro.resourceserver.repository.WorkLocationRepository;
import com.bindord.jaipro.resourceserver.service.specialist.WorkLocationService;
import io.r2dbc.spi.Connection;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

import static com.bindord.jaipro.resourceserver.utils.Utilitarios.getNullPropertyNames;

@AllArgsConstructor
@Service
public class WorkLocationServiceImpl implements WorkLocationService {

    private final WorkLocationRepository repository;

    @Override
    public Mono<WorkLocation> save(WorkLocationDto entity) throws NotFoundValidationException, CustomValidationException {
        return repository.save(convertToEntityForNewCase(entity));
    }

    @Override
    public Mono<WorkLocation> update(WorkLocationDto entity) throws NotFoundValidationException, CustomValidationException {
        Mono<WorkLocation> qWorkLocation = repository.findById(entity.getSpecialistId());
        return qWorkLocation.flatMap(qCus -> repository.save(convertToEntity(entity, qCus)));
    }

    @Override
    public Mono<WorkLocation> findOne(UUID id) throws NotFoundValidationException {
        return repository.findById(id);
    }

    private <T> Mono<T> close(Connection connection) {
        return Mono.from(connection.close())
                .then(Mono.empty());
    }

    @Override
    public Mono<Void> delete(UUID id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono<Void> delete() {
        return repository.deleteAll();
    }

    @Override
    public Flux<WorkLocation> findAll() {
        return repository.findAll();
    }

    @Override
    public Flux<WorkLocation> findAllBySpecialistId(UUID id) {
        return repository.findAllById(List.of(id));
    }

    @Override
    public Flux<WorkLocation> saveAll(Iterable<WorkLocation> workLocations) {
        workLocations.forEach(e->e.setNew(true));
        return repository.saveAll(workLocations);
    }

    @Override
    public Mono<Void> deleteWorkLocationBySpecialistId(UUID specialistId, int districtId) {
        return repository
                .deleteBySpecialistIdAndDistrictId(specialistId, districtId)
                .then(Mono.empty());
    }

    @Override
    public Flux<WorkLocation> findAllNative() {
        return repository.findAll();
    }


    private WorkLocation convertToEntity(WorkLocationDto obj, WorkLocation workLocation) {
        BeanUtils.copyProperties(obj, workLocation, getNullPropertyNames(obj));
        return workLocation;
    }

    private WorkLocation convertToEntityForNewCase(WorkLocationDto obj) {
        var workLocation = new WorkLocation();
        BeanUtils.copyProperties(obj, workLocation);
        workLocation.setNew(true);
        return workLocation;
    }
}
