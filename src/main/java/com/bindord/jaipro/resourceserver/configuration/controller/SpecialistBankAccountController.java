package com.bindord.jaipro.resourceserver.configuration.controller;

import com.bindord.jaipro.resourceserver.advice.CustomValidationException;
import com.bindord.jaipro.resourceserver.advice.NotFoundValidationException;
import com.bindord.jaipro.resourceserver.domain.specialist.SpecialistBankAccount;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistBankAccountDto;
import com.bindord.jaipro.resourceserver.service.specialist.SpecialistBankAccountService;
import com.bindord.jaipro.resourceserver.validator.Validator;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("${service.ingress.context-path}/specialist-bank-account")
public class SpecialistBankAccountController {

    private final Validator validator;

    private SpecialistBankAccountService specialistBankAccountService;

    @ApiResponse(description = "Persist a specialist bank account", responseCode = "200")
    @PostMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<SpecialistBankAccount> save(@Valid @RequestBody SpecialistBankAccountDto specialistBankAccount)
            throws NotFoundValidationException, CustomValidationException {
        return specialistBankAccountService.save(specialistBankAccount);
    }

    @ApiResponse(description = "Update a specialist bank account", responseCode = "200")
    @PutMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<SpecialistBankAccount> update(@Valid @RequestBody SpecialistBankAccountDto specialistBankAccount)
            throws NotFoundValidationException, CustomValidationException {
        return specialistBankAccountService.update(specialistBankAccount);
    }

    @ApiResponse(description = "Find All by specialist Id", responseCode = "200")
    @GetMapping(value = "/list/{specialistId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Flux<SpecialistBankAccount> findAll(@PathVariable UUID specialistId) throws NotFoundValidationException {
        return specialistBankAccountService.findAllBySpecialistId(specialistId);
    }

    @ApiResponse(description = "Find One by id", responseCode = "200")
    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<SpecialistBankAccount> findOne(@PathVariable UUID id) throws NotFoundValidationException {
        return specialistBankAccountService.findOne(id);
    }

    @ApiResponse(description = "Delete banks account", responseCode = "200")
    @DeleteMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Void> deleteBankAccount(@PathVariable UUID id) {
        return specialistBankAccountService.deleteBankAccount(id);
    }
}
