package com.bindord.jaipro.resourceserver.service.specialist;

import com.bindord.jaipro.resourceserver.domain.specialist.SpecialistBankAccount;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistBankAccountDto;
import com.bindord.jaipro.resourceserver.generic.BaseService;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface SpecialistBankAccountService extends BaseService<SpecialistBankAccount, UUID, SpecialistBankAccountDto, SpecialistBankAccountDto> {

    Flux<SpecialistBankAccount> findAllBySpecialistId(UUID specialistId);

}
