package com.bindord.jaipro.resourceserver.repository;

import com.bindord.jaipro.resourceserver.domain.specialist.SpecialistBankAccount;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface SpecialistBankAccountRepository extends ReactiveCrudRepository<SpecialistBankAccount, UUID> {

    public Flux<SpecialistBankAccount> findAllBySpecialistId(UUID specialistId);
}
