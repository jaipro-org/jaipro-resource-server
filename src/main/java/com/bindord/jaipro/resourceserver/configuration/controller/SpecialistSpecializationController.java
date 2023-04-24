package com.bindord.jaipro.resourceserver.configuration.controller;

import com.bindord.jaipro.resourceserver.advice.CustomValidationException;
import com.bindord.jaipro.resourceserver.advice.NotFoundValidationException;
import com.bindord.jaipro.resourceserver.domain.specialist.SpecialistSpecialization;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistSpecializationDto;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistSpecializationUpdateDto;
import com.bindord.jaipro.resourceserver.service.specialist.SpecialistSpecializationService;
import com.bindord.jaipro.resourceserver.validator.Validator;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
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
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("${service.ingress.context-path}/specialist-specialization")
@Validated
public class SpecialistSpecializationController {

    private final Validator validator;

    private final SpecialistSpecializationService specialistSpecializationService;

    @ApiResponse(description = "Persist a specialistSpecialization",
            responseCode = "200")
    @PostMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<SpecialistSpecialization> save(@Valid @RequestBody SpecialistSpecializationDto specialistSpecialization)
            throws NotFoundValidationException, CustomValidationException {
        return specialistSpecializationService.save(specialistSpecialization);
    }

    @ApiResponse(description = "Persist many specialistSpecializations",
            responseCode = "200")
    @PostMapping(value = "/list",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Flux<SpecialistSpecialization> saveAll(@RequestBody List<@Valid SpecialistSpecialization> specialistSpecializations) {
        return specialistSpecializationService.saveAll(specialistSpecializations);
    }

    @ApiResponse(description = "Update a specialistSpecialization",
            responseCode = "200")
    @PutMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<SpecialistSpecialization> update(@Valid @RequestBody SpecialistSpecializationUpdateDto specialistSpecialization)
            throws NotFoundValidationException, CustomValidationException {
        return specialistSpecializationService.update(specialistSpecialization);
    }

    @ApiResponse(description = "List specialistSpecializations",
            responseCode = "200")
    @GetMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Flux<SpecialistSpecialization> findAll() {
        return specialistSpecializationService.findAll();
    }

    @ApiResponse(description = "FindAll by SpecialistId",
            responseCode = "200")
    @GetMapping(value = "/list/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Flux<SpecialistSpecialization> findAllBySpecialistId(@PathVariable UUID id) throws NotFoundValidationException {
        return specialistSpecializationService.findAllBySpecialistId(id);
    }

}
