package com.bindord.jaipro.resourceserver.service.profession.impl;

import com.bindord.jaipro.resourceserver.advice.CustomValidationException;
import com.bindord.jaipro.resourceserver.advice.NotFoundValidationException;
import com.bindord.jaipro.resourceserver.domain.profession.Profession;
import com.bindord.jaipro.resourceserver.domain.profession.dto.ProfessionDto;
import com.bindord.jaipro.resourceserver.domain.profession.dto.ProfessionUpdateDto;
import com.bindord.jaipro.resourceserver.repository.ProfessionRepository;
import com.bindord.jaipro.resourceserver.service.profession.ProfessionService;
import com.bindord.jaipro.resourceserver.utils.Utilitarios;
import io.r2dbc.spi.Connection;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class ProfessionServiceImpl implements ProfessionService {

    private final ProfessionRepository repository;

    @Override
    public Mono<Profession> save(ProfessionDto entity) throws NotFoundValidationException, CustomValidationException {
        return repository.save(convertToEntityForNewCase(entity));
    }

    @Override
    public Mono<Profession> update(ProfessionUpdateDto entity) throws NotFoundValidationException, CustomValidationException {
        Mono<Profession> qProfession = repository.findById(entity.getId());
        return qProfession.flatMap(qCus -> repository.save(convertToEntity(entity, qCus)));
    }

    @Override
    public Mono<Profession> findOne(Integer id) throws NotFoundValidationException {
        return repository.findById(id);
    }

    private <T> Mono<T> close(Connection connection) {
        return Mono.from(connection.close())
                .then(Mono.empty());
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public Mono<Void> delete() {
        return repository.deleteAll();
    }

    @Override
    public Flux<Profession> findAll() {
        return repository.findAll();
    }

    @Override
    public Flux<Profession> findAllNative() {
        return repository.findAll();
    }


    private Profession convertToEntity(ProfessionUpdateDto obj, Profession profession) {
        BeanUtils.copyProperties(obj, profession, Utilitarios.getNullPropertyNames(obj));
        return profession;
    }

    private Profession convertToEntityForNewCase(ProfessionDto obj) {
        var profession = new Profession();
        BeanUtils.copyProperties(obj, profession);
        profession.setNew(true);
        return profession;
    }
}
