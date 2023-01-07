package com.bindord.jaipro.resourceserver.service.specialist.impl;

import com.bindord.jaipro.resourceserver.advice.CustomValidationException;
import com.bindord.jaipro.resourceserver.advice.NotFoundValidationException;
import com.bindord.jaipro.resourceserver.domain.specialist.SpecialistCv;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistCvDto;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistCvUpdateDto;
import com.bindord.jaipro.resourceserver.repository.SpecialistCvRepository;
import com.bindord.jaipro.resourceserver.service.specialist.SpecialistCvService;
import io.r2dbc.postgresql.codec.Json;
import io.r2dbc.spi.Connection;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.bindord.jaipro.resourceserver.utils.Utilitarios.getNullPropertyNames;
import static com.bindord.jaipro.resourceserver.utils.Utilitarios.instanceObjectMapper;
import static java.util.Objects.isNull;
import static org.springframework.util.CollectionUtils.isEmpty;

@AllArgsConstructor
@Service
public class SpecialistCvServiceImpl implements SpecialistCvService {

    private final SpecialistCvRepository repository;

    @Override
    public Mono<SpecialistCv> save(SpecialistCvDto entity) throws NotFoundValidationException, CustomValidationException {
        return repository.save(convertToEntityForNewCase(entity));
    }

    @Override
    public Mono<SpecialistCv> update(SpecialistCvUpdateDto entity) throws NotFoundValidationException, CustomValidationException {
        Mono<SpecialistCv> qSpecialistCv = repository.findById(entity.getId());
        return qSpecialistCv.flatMap(qCus -> repository.save(convertToEntity(entity, qCus)));
    }

    @Override
    public Mono<SpecialistCv> findOne(UUID id) throws NotFoundValidationException {
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
    public Flux<SpecialistCv> findAll() {
        return repository.findAll();
    }

    @Override
    public Flux<SpecialistCv> findAllNative() {
        return repository.findAll();
    }


    @SneakyThrows
    private SpecialistCv convertToEntity(SpecialistCvUpdateDto obj, SpecialistCv specialistCv) {
        BeanUtils.copyProperties(obj, specialistCv, getNullPropertyNames(obj));
        var objMapper = instanceObjectMapper();
        if(!isEmpty(obj.getSocialNetworks()))
            specialistCv.setSocialNetworks(
                    Json.of(
                            objMapper.writeValueAsString(obj.getSocialNetworks())
                    ));
        if(!isEmpty(obj.getGallery()))
            specialistCv.setGallery(
                    Json.of(
                            objMapper.writeValueAsString(obj.getGallery())
                    ));
        if(!isEmpty(obj.getExperienceTimes()))
            specialistCv.setExperienceTimes(
                    Json.of(
                            objMapper.writeValueAsString(obj.getExperienceTimes())
                    ));
        if(!isNull(obj.getProfilePhoto()))
            specialistCv.setProfilePhoto(
                    Json.of(
                            objMapper.writeValueAsString(obj.getProfilePhoto())
                    )
            );
        return specialistCv;
    }

    private SpecialistCv convertToEntityForNewCase(SpecialistCvDto obj) {
        var specialistCv = new SpecialistCv();
        BeanUtils.copyProperties(obj, specialistCv);
        serializeJsonColumns(specialistCv, obj);
        specialistCv.setNew(true);
        return specialistCv;
    }

    @SneakyThrows
    private void serializeJsonColumns(SpecialistCv specialistCv,
                                      SpecialistCvDto obj) {
        var objMapper = instanceObjectMapper();
        specialistCv.setSocialNetworks(
                Json.of(
                        objMapper.writeValueAsString(obj.getSocialNetworks())
                )
        );
        specialistCv.setGallery(
                Json.of(
                        objMapper.writeValueAsString(obj.getGallery())
                )
        );
        specialistCv.setProfilePhoto(
                Json.of(
                        objMapper.writeValueAsString(obj.getProfilePhoto())
                )
        );
        specialistCv.setExperienceTimes(
                Json.of(
                        objMapper.writeValueAsString(obj.getExperienceTimes())
                )
        );
    }
}
