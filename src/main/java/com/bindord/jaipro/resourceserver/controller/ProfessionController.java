package com.bindord.jaipro.resourceserver.controller;

import com.bindord.jaipro.resourceserver.advice.CustomValidationException;
import com.bindord.jaipro.resourceserver.advice.NotFoundValidationException;
import com.bindord.jaipro.resourceserver.domain.profession.Profession;
import com.bindord.jaipro.resourceserver.domain.profession.dto.ProfessionDto;
import com.bindord.jaipro.resourceserver.domain.profession.dto.ProfessionUpdateDto;
import com.bindord.jaipro.resourceserver.service.profession.ProfessionService;
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
@RequestMapping("${service.ingress.context-path}/profession")
public class ProfessionController {

    private final Validator validator;

    private final ProfessionService professionService;

    @ApiResponse(description = "Persist a profession",
            responseCode = "200")
    @PostMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Profession> save(@Valid @RequestBody ProfessionDto profession)
            throws NotFoundValidationException, CustomValidationException {
        return professionService.save(profession);
    }

    @ApiResponse(description = "Update a profession",
            responseCode = "200")
    @PutMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Profession> update(@Valid @RequestBody ProfessionUpdateDto profession)
            throws NotFoundValidationException, CustomValidationException {
        return professionService.update(profession);
    }

    @ApiResponse(description = "List professions",
            responseCode = "200")
    @GetMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Flux<Profession> findAll() {
        return professionService.findAll();
    }

}
