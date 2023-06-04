package com.bindord.jaipro.resourceserver.service.country.impl;

import com.bindord.jaipro.resourceserver.advice.CustomValidationException;
import com.bindord.jaipro.resourceserver.advice.NotFoundValidationException;
import com.bindord.jaipro.resourceserver.domain.country.District;
import com.bindord.jaipro.resourceserver.domain.country.dto.DistrictDto;
import com.bindord.jaipro.resourceserver.domain.country.dto.DistrictUpdateDto;
import com.bindord.jaipro.resourceserver.repository.DistrictRepository;
import com.bindord.jaipro.resourceserver.service.country.DistrictService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.bindord.jaipro.resourceserver.utils.Utilitarios.getNullPropertyNames;

@AllArgsConstructor
@Service
public class DistrictServiceImpl implements DistrictService {

    private final DistrictRepository repository;

    @Override
    public Mono<District> save(DistrictDto entity) throws NotFoundValidationException, CustomValidationException {
        return repository.save(convertToEntityForNewCase(entity));
    }

    @Override
    public Mono<District> update(DistrictUpdateDto entity) throws NotFoundValidationException, CustomValidationException {
        Mono<District> qDistrict = repository.findById(entity.getId());
        return qDistrict.flatMap(qCus -> repository.save(convertToEntity(entity, qCus)));
    }

    @Override
    public Mono<District> findOne(Integer id) throws NotFoundValidationException {
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
    public Flux<District> findAll() {
        return repository.findAll();
    }

    @Override
    public Flux<District> findAllNative() {
        return repository.findAll();
    }


    private District convertToEntity(DistrictUpdateDto obj, District district) {
        BeanUtils.copyProperties(obj, district, getNullPropertyNames(obj));
        return district;
    }

    private District convertToEntityForNewCase(DistrictDto obj) {
        var district = new District();
        BeanUtils.copyProperties(obj, district);
        return district;
    }
}
