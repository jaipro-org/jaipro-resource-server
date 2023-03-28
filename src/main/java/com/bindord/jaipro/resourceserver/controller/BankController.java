package com.bindord.jaipro.resourceserver.controller;

import com.bindord.jaipro.resourceserver.advice.CustomValidationException;
import com.bindord.jaipro.resourceserver.advice.NotFoundValidationException;
import com.bindord.jaipro.resourceserver.domain.bank.Bank;
import com.bindord.jaipro.resourceserver.domain.bank.dto.BankDto;
import com.bindord.jaipro.resourceserver.domain.bank.dto.BankUpdateDto;
import com.bindord.jaipro.resourceserver.service.bank.BankService;
import com.bindord.jaipro.resourceserver.validator.Validator;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("${service.ingress.context-path}/bank")
public class BankController {

    private final Validator validator;

    private final BankService bankService;

    @ApiResponse(description = "Persist a bank",
            responseCode = "200")
    @PostMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Bank> save(@Valid @RequestBody BankDto bank)
            throws NotFoundValidationException, CustomValidationException {
        return bankService.save(bank);
    }

    @ApiResponse(description = "Update a bank",
            responseCode = "200")
    @PutMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Bank> update(@Valid @RequestBody BankUpdateDto bank)
            throws NotFoundValidationException, CustomValidationException {
        return bankService.update(bank);
    }

    @ApiResponse(description = "List banks",
            responseCode = "200")
    @GetMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Flux<Bank> findAll() {
        return bankService.findAll();
    }

}
