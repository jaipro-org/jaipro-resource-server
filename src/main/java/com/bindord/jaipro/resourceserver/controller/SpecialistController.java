package com.bindord.jaipro.resourceserver.controller;

import com.bindord.jaipro.resourceserver.advice.CustomValidationException;
import com.bindord.jaipro.resourceserver.advice.NotFoundValidationException;
import com.bindord.jaipro.resourceserver.domain.specialist.Specialist;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistDto;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistSearchDto;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistUpdateDto;
import com.bindord.jaipro.resourceserver.service.specialist.SpecialistService;
import com.bindord.jaipro.resourceserver.validator.Validator;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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
    @PutMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Specialist> update(@PathVariable UUID id, @Valid @RequestBody SpecialistUpdateDto specialist)
            throws NotFoundValidationException, CustomValidationException {
        return specialistService.updatePresentation(id, specialist);
    }

    @ApiResponse(description = "List specialists",
            responseCode = "200")
    @GetMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Flux<Specialist> findAll() {
        return specialistService.findAll();
    }

    @ApiResponse(description = "Find by id",
            responseCode = "200")
    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Specialist> findById(@PathVariable UUID id) throws NotFoundValidationException {
        return specialistService.findOne(id);
    }

    @ApiResponse(description = "Find by filters",
            responseCode = "200")
    @GetMapping(value = "/search",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Flux<Specialist> search(@Valid @RequestBody SpecialistSearchDto specialistSearchDto) throws NotFoundValidationException {
        return Flux.just(new Specialist());
    }

    @ApiResponse(description = "Verify if specialist exists",
            responseCode = "200")
    @GetMapping(value = "/by/query",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Boolean> existsSpecialistByDocument(@RequestParam String document) throws NotFoundValidationException {
        return specialistService.existsSpecialistByDocument(document);
    }
}
