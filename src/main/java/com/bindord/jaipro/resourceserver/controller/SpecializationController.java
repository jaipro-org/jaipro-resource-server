package com.bindord.jaipro.resourceserver.controller;

import com.bindord.jaipro.resourceserver.advice.CustomValidationException;
import com.bindord.jaipro.resourceserver.advice.NotFoundValidationException;
import com.bindord.jaipro.resourceserver.domain.specialist.Specialization;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecializationDto;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecializationUpdateDto;
import com.bindord.jaipro.resourceserver.service.specialist.SpecializationService;
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
@RequestMapping("${service.ingress.context-path}/specialization")
public class SpecializationController {

    private final Validator validator;

    private final SpecializationService specializationService;

    @ApiResponse(description = "Persist a specialization",
            responseCode = "200")
    @PostMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Specialization> save(@Valid @RequestBody SpecializationDto specialization)
            throws NotFoundValidationException, CustomValidationException {
        return specializationService.save(specialization);
    }

    @ApiResponse(description = "Update a specialization",
            responseCode = "200")
    @PutMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Specialization> update(@Valid @RequestBody SpecializationUpdateDto specialization)
            throws NotFoundValidationException, CustomValidationException {
        return specializationService.update(specialization);
    }

    @ApiResponse(description = "List specializations",
            responseCode = "200")
    @GetMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Flux<Specialization> findAll() {
        return specializationService.findAll();
    }

}
