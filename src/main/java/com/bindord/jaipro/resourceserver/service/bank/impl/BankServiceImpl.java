package com.bindord.jaipro.resourceserver.service.bank.impl;

import com.bindord.jaipro.resourceserver.advice.CustomValidationException;
import com.bindord.jaipro.resourceserver.advice.NotFoundValidationException;
import com.bindord.jaipro.resourceserver.domain.bank.Bank;
import com.bindord.jaipro.resourceserver.domain.bank.dto.BankDto;
import com.bindord.jaipro.resourceserver.domain.bank.dto.BankUpdateDto;
import com.bindord.jaipro.resourceserver.repository.BankRepository;
import com.bindord.jaipro.resourceserver.service.bank.BankService;
import io.r2dbc.spi.Connection;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.bindord.jaipro.resourceserver.utils.Utilitarios.getNullPropertyNames;

@AllArgsConstructor
@Service
public class BankServiceImpl implements BankService {

    private final BankRepository repository;

    @Override
    public Mono<Bank> save(BankDto entity) throws NotFoundValidationException, CustomValidationException {
        return repository.save(convertToEntityForNewCase(entity));
    }

    @Override
    public Mono<Bank> update(BankUpdateDto entity) throws NotFoundValidationException, CustomValidationException {
        Mono<Bank> qBank = repository.findById(entity.getId());
        return qBank.flatMap(qCus -> repository.save(convertToEntity(entity, qCus)));
    }

    @Override
    public Mono<Bank> findOne(Integer id) throws NotFoundValidationException {
        return repository.findById(id);
    }

    private <T> Mono<T> close(Connection connection) {
        return Mono.from(connection.close())
                .then(Mono.empty());
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
    public Flux<Bank> findAll() {
        return repository.findAll();
    }

    private Bank convertToEntity(BankUpdateDto obj, Bank bank) {
        BeanUtils.copyProperties(obj, bank, getNullPropertyNames(obj));
        return bank;
    }

    private Bank convertToEntityForNewCase(BankDto obj) {
        var bank = new Bank();
        BeanUtils.copyProperties(obj, bank);
        return bank;
    }
}
