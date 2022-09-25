package com.bindord.eureka.resourceserver.controller;

import com.bindord.eureka.resourceserver.advice.CustomValidationException;
import com.bindord.eureka.resourceserver.advice.NotFoundValidationException;
import com.bindord.eureka.resourceserver.domain.specialist.Specialist;
import com.bindord.eureka.resourceserver.domain.specialist.dto.SpecialistDto;
import com.bindord.eureka.resourceserver.domain.specialist.dto.SpecialistUpdateDto;
import com.bindord.eureka.resourceserver.service.specialist.SpecialistService;
import com.bindord.eureka.resourceserver.validator.Validator;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.UUID;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("${service.ingress.context-path}/specialist")
public class SpecialistController {

    private final Validator validator;

    private final SpecialistService specialistService;

    @ApiResponse(description = "Persist a specialist",
            responseCode = "200")
    @PostMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Specialist> save(@Valid @RequestBody SpecialistDto specialist)
            throws NotFoundValidationException, CustomValidationException {
        return specialistService.save(specialist);
    }

    @ApiResponse(description = "Update a specialist",
            responseCode = "200")
    @PutMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Specialist> update(@Valid @RequestBody SpecialistUpdateDto specialist)
            throws NotFoundValidationException, CustomValidationException {
        return specialistService.update(specialist);
    }

    @ApiResponse(description = "List specialists",
            responseCode = "200")
    @GetMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Flux<Specialist> findAll() {
        return specialistService.findAll();
    }

    @PreAuthorize("hasRole('UMA_AUTHORIZATION')")
    @ApiResponse(description = "Find by id",
            responseCode = "200")
    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Specialist> findById(@PathVariable UUID id) throws NotFoundValidationException {
        return specialistService.findOne(id);
    }

    @ApiResponse(description = "Find by id with queryParam",
            responseCode = "200")
    @GetMapping(value = "/by/query",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Boolean> findByIdWithQuery(@RequestParam String document) throws NotFoundValidationException {
        return specialistService.existsSpecialistByDocument(document);
    }
}
