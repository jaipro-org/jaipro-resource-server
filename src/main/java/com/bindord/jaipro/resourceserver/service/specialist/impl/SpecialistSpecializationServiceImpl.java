package com.bindord.jaipro.resourceserver.service.specialist.impl;

import com.bindord.jaipro.resourceserver.advice.CustomValidationException;
import com.bindord.jaipro.resourceserver.advice.NotFoundValidationException;
import com.bindord.jaipro.resourceserver.domain.specialist.SpecialistSpecialization;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistSpecializationDto;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistSpecializationUpdateDto;
import com.bindord.jaipro.resourceserver.repository.SpecialistSpecializationRepository;
import com.bindord.jaipro.resourceserver.service.specialist.SpecialistSpecializationService;
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
public class SpecialistSpecializationServiceImpl implements SpecialistSpecializationService {

    private final SpecialistSpecializationRepository repository;

    @Override
    public Mono<SpecialistSpecialization> save(SpecialistSpecializationDto entity) throws NotFoundValidationException, CustomValidationException {
        return repository.save(convertToEntityForNewCase(entity));
    }

    @Override
    public Mono<SpecialistSpecialization> update(SpecialistSpecializationDto entity) throws NotFoundValidationException, CustomValidationException {
        Mono<SpecialistSpecialization> qSpecialistSpecialization = repository.findById(entity.getSpecialistId());
        return qSpecialistSpecialization.flatMap(qCus -> repository.save(convertToEntity(entity, qCus)));
    }

    @Override
    public Mono<SpecialistSpecialization> findOne(UUID id) throws NotFoundValidationException {
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
    public Flux<SpecialistSpecialization> findAll() {
        return repository.findAll();
    }

    @Override
    public Flux<SpecialistSpecialization> findAllBySpecialistId(UUID id) {
        return repository.findAllById(List.of(id));
    }

    @Override
    public Flux<SpecialistSpecialization> saveAll(Iterable<SpecialistSpecialization> specialistSpecializations) {
        specialistSpecializations.forEach(e->e.setNew(true));
        return repository.saveAll(specialistSpecializations);
    }

    @Override
    public Mono<Boolean> UpdateExperience(SpecialistSpecializationUpdateDto entity) {
        Flux<SpecialistSpecialization> qSpecialistSpecialization = repository.findAllByProfessionId(entity.getProfessionId());
        return qSpecialistSpecialization.map(x -> {
           if(!entity.getListSpecialitiesIds().contains(x.getSpecializationId())){
               SpecialistSpecialization specialistSpecialization = new SpecialistSpecialization();
               specialistSpecialization.setSpecialistId(x.getSpecialistId());
               specialistSpecialization.setProfessionId(x.getProfessionId());

               repository.save(specialistSpecialization);
           }
            return null;
        }).then(Mono.just(true));
    }

    @Override
    public Flux<SpecialistSpecialization> findAllNative() {
        return repository.findAll();
    }


    private SpecialistSpecialization convertToEntity(SpecialistSpecializationDto obj, SpecialistSpecialization workLocation) {
        BeanUtils.copyProperties(obj, workLocation, getNullPropertyNames(obj));
        return workLocation;
    }

    private SpecialistSpecialization convertToEntityForNewCase(SpecialistSpecializationDto obj) {
        var workLocation = new SpecialistSpecialization();
        BeanUtils.copyProperties(obj, workLocation);
        workLocation.setNew(true);
        return workLocation;
    }
}
