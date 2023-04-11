package com.bindord.jaipro.resourceserver.repository;

import com.bindord.jaipro.resourceserver.domain.bank.Bank;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends ReactiveCrudRepository<Bank, Integer> {

}
