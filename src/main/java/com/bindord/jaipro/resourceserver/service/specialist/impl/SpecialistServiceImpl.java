package com.bindord.jaipro.resourceserver.service.specialist.impl;

import com.bindord.jaipro.resourceserver.advice.CustomValidationException;
import com.bindord.jaipro.resourceserver.advice.NotFoundValidationException;
import com.bindord.jaipro.resourceserver.domain.base.BasePaginateResponse;
import com.bindord.jaipro.resourceserver.domain.json.Photo;
import com.bindord.jaipro.resourceserver.domain.json.Rating;
import com.bindord.jaipro.resourceserver.domain.specialist.Specialist;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistDto;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistFiltersSearchDto;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistPublicInformationDto;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistResultSearchDTO;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistUpdateDto;
import com.bindord.jaipro.resourceserver.repository.SpecialistRepository;
import com.bindord.jaipro.resourceserver.service.specialist.SpecialistService;
import com.bindord.jaipro.resourceserver.utils.Utilitarios;
import com.fasterxml.jackson.core.type.TypeReference;
import io.r2dbc.postgresql.codec.Json;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.bindord.jaipro.resourceserver.utils.Utilitarios.instanceObjectMapper;

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

    @Override
    public Mono<Boolean> existsSpecialistByDocument(String document) {
        return repository.existsSpecialistByDocument(document);
    }

    @Override
    public Mono<Specialist> updatePresentation(UUID id, SpecialistUpdateDto specialistUpdateDto) {
        Mono<Specialist> qSpecialist = repository.findById(id);
        return qSpecialist
                .flatMap(qCus -> repository.save(convertToEntity(specialistUpdateDto, qCus)));
    }

    @Override
    public Mono<BasePaginateResponse<SpecialistResultSearchDTO>> searchSpecialist(SpecialistFiltersSearchDto filters) {
        String paramIdCategories = generateStrPostgreArrayByList(filters.getCategories());
        String paramSpecializations = generateStrPostgreArrayByList(filters.getSpecialties());
        String paramUbigeums = generateStrPostgreArrayByList(filters.getDistricts());

        var data = repository.searchSpecialist(paramIdCategories, paramSpecializations, paramUbigeums, filters.getPageNumber(), filters.getPageSize());
        var totalRows = repository.getTotalRowsInSearchSpecialist(paramIdCategories, paramSpecializations, paramUbigeums);
        return Mono.zip(data.collectList(), totalRows)
                    .flatMap(tuple -> {
                        return Mono.just(new BasePaginateResponse<SpecialistResultSearchDTO>(tuple.getT2(), tuple.getT1()));
                    });
    }

    public Flux<Rating> getRatings(UUID id){
        return repository
                    .findById(id)
                    .flatMap(x -> {
                        var list = convertJsonToClassRating(x.getRatings());
                        return Mono.just(list);
                    }).flatMapMany(Flux::fromIterable);
    }

    @Override
    public Mono<SpecialistPublicInformationDto> getPublicInformation(UUID id) {
        return repository.getPublicInformationById(id);
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
    public Flux<Specialist> findAll() {
        return repository.findAll();
    }

    @Override
    public Flux<Specialist> findAllNative() {
        return repository.findAll();
    }


    private Specialist convertToEntity(SpecialistUpdateDto obj, Specialist specialist) {
        BeanUtils.copyProperties(obj, specialist, Utilitarios.getNullPropertyNames(obj));
        return specialist;
    }

    private Specialist convertToEntityForNewCase(SpecialistDto obj) {
        var specialist = new Specialist();
        BeanUtils.copyProperties(obj, specialist);
        specialist.setNew(true);
        specialist.setVerified(false);
        return specialist;
    }

    private String generateStrPostgreArrayByList(List<Integer> list){
        String p_list = (list == null || list.isEmpty()) ? "" : "{" + String.join(",", list.stream().map(String::valueOf).toArray(String[]::new)) + "}";
        return p_list;
    }

    @SneakyThrows
    private List<Rating> convertJsonToClassRating(Json json) {
        if(json == null)
            return new ArrayList<Rating>();

        var objectMapper = instanceObjectMapper();
        return objectMapper.readValue(json.asString(), new TypeReference<>() {});
    }
}