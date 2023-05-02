package com.bindord.jaipro.resourceserver.service.specialist.impl;

import com.bindord.jaipro.resourceserver.advice.CustomValidationException;
import com.bindord.jaipro.resourceserver.advice.NotFoundValidationException;
import com.bindord.jaipro.resourceserver.domain.specialist.SpecialistBankAccount;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistBankAccountDto;
import com.bindord.jaipro.resourceserver.repository.SpecialistBankAccountRepository;
import com.bindord.jaipro.resourceserver.service.specialist.SpecialistBankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.bindord.jaipro.resourceserver.utils.Constants.ERROR_BANK_ACCOUNT_REMOVED_PREFERED;
import static com.bindord.jaipro.resourceserver.utils.Utilitarios.getNullPropertyNames;
import java.util.UUID;

@AllArgsConstructor
@Service
public class SpecialistBankAccountServiceImpl implements SpecialistBankAccountService {

    private final SpecialistBankAccountRepository repository;

    @Override
    public Mono<SpecialistBankAccount> save(SpecialistBankAccountDto entity) throws NotFoundValidationException, CustomValidationException {
        return repository.save(convertToEntityForNewCase(entity));
    }

    @Override
    public Mono<SpecialistBankAccount> update(SpecialistBankAccountDto entity) throws NotFoundValidationException, CustomValidationException {
        Mono<SpecialistBankAccount> qSpecialistBankAccount = repository.findById(entity.getId());
        return qSpecialistBankAccount.flatMap(qCus -> repository.save(convertToEntity(entity, qCus)));
    }

    @Override
    public Mono<SpecialistBankAccount> findOne(UUID id) throws NotFoundValidationException {
        return repository.findById(id);
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
    public Flux<SpecialistBankAccount> findAll() {
        return repository.findAll();
    }

    @Override
    public Flux<SpecialistBankAccount> findAllBySpecialistId(UUID specialistId) {
        return repository.findAllBySpecialistId(specialistId);
    }

    @Override
    public Mono<Void> deleteBankAccount(UUID specialistBankAccountId) {
        return repository.findById(specialistBankAccountId)
                        .flatMap(qba -> {
                            if(qba.isPreferred())
                                return Mono.error(new CustomValidationException(ERROR_BANK_ACCOUNT_REMOVED_PREFERED));

                            return repository.deleteById(specialistBankAccountId);
                        }).then(Mono.empty());
    }

    private SpecialistBankAccount convertToEntityForNewCase(SpecialistBankAccountDto obj){
        var specialistBankAccount = new SpecialistBankAccount();
        BeanUtils.copyProperties(obj, specialistBankAccount);
        specialistBankAccount.setNew(true);

        return specialistBankAccount;
    }

    private SpecialistBankAccount convertToEntity(SpecialistBankAccountDto obj, SpecialistBankAccount specialistBankAccount){
        BeanUtils.copyProperties(obj, specialistBankAccount, getNullPropertyNames(obj));
        return specialistBankAccount;
    }
}
