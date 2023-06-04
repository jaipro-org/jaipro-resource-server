package com.bindord.jaipro.resourceserver.service.specialist.impl;

import com.bindord.jaipro.resourceserver.advice.CustomValidationException;
import com.bindord.jaipro.resourceserver.advice.NotFoundValidationException;
import com.bindord.jaipro.resourceserver.domain.specialist.Specialization;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecializationDto;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecializationUpdateDto;
import com.bindord.jaipro.resourceserver.repository.SpecializationRepository;
import com.bindord.jaipro.resourceserver.service.specialist.SpecializationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.bindord.jaipro.resourceserver.utils.Utilitarios.getNullPropertyNames;

@AllArgsConstructor
@Service
public class SpecializationServiceImpl implements SpecializationService {

    private final SpecializationRepository repository;

    @Override
    public Mono<Specialization> save(SpecializationDto entity) throws NotFoundValidationException, CustomValidationException {
        return repository.save(convertToEntityForNewCase(entity));
    }

    @Override
    public Mono<Specialization> update(SpecializationUpdateDto entity) throws NotFoundValidationException, CustomValidationException {
        Mono<Specialization> qSpecialization = repository.findById(entity.getId());
        return qSpecialization.flatMap(qCus -> repository.save(convertToEntity(entity, qCus)));
    }

    @Override
    public Mono<Specialization> findOne(Integer id) throws NotFoundValidationException {
        return repository.findById(id);
    }

    @Override
    public Mono<Void> delete(Integer id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono<Void> delete() {
        return repository.deleteAll();
    }

    @Override
    public Flux<Specialization> findAll() {
        return repository.findAll();
    }

    @Override
    public Flux<Specialization> findAllNative() {
        return repository.findAll();
    }


    private Specialization convertToEntity(SpecializationUpdateDto obj, Specialization specialization) {
        BeanUtils.copyProperties(obj, specialization, getNullPropertyNames(obj));
        return specialization;
    }

    private Specialization convertToEntityForNewCase(SpecializationDto obj) {
        var specialization = new Specialization();
        BeanUtils.copyProperties(obj, specialization);
        return specialization;
    }
}
