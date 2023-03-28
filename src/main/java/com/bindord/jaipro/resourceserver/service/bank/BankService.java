package com.bindord.jaipro.resourceserver.service.bank;

import com.bindord.jaipro.resourceserver.domain.bank.Bank;
import com.bindord.jaipro.resourceserver.domain.bank.dto.BankDto;
import com.bindord.jaipro.resourceserver.domain.bank.dto.BankUpdateDto;
import com.bindord.jaipro.resourceserver.generic.BaseService;
import org.springframework.stereotype.Repository;

@Repository
public interface BankService extends BaseService<Bank, Integer, BankDto, BankUpdateDto> {

}
