package com.bindord.jaipro.resourceserver.controller;

import com.bindord.jaipro.resourceserver.advice.CustomValidationException;
import com.bindord.jaipro.resourceserver.advice.NotFoundValidationException;
import com.bindord.jaipro.resourceserver.domain.specialist.SpecialistCv;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistCvDto;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistCvUpdateDto;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistExperienceUpdateDto;
import com.bindord.jaipro.resourceserver.service.specialist.SpecialistCvService;
import com.bindord.jaipro.resourceserver.validator.Validator;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("${service.ingress.context-path}/specialist-cv")
public class SpecialistCvController {

    private final Validator validator;

    private final SpecialistCvService specialistCvService;

    @ApiResponse(description = "Persist a specialist cv",
            responseCode = "200")
    @PostMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<SpecialistCv> save(@Valid @RequestBody SpecialistCvDto specialist)
            throws NotFoundValidationException, CustomValidationException {
        return specialistCvService.save(specialist);
    }

    @ApiResponse(description = "Update a specialist cv",
            responseCode = "200")
    @PutMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<SpecialistCv> update(@Valid @RequestBody SpecialistCvUpdateDto specialist)
            throws NotFoundValidationException, CustomValidationException {
        return specialistCvService.update(specialist);
    }

    @ApiResponse(description = "List specialist's cvs",
            responseCode = "200")
    @GetMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Flux<SpecialistCv> findAll() {
        return specialistCvService.findAll();
    }

    @PreAuthorize("hasRole('UMA_AUTHORIZATION')")
    @ApiResponse(description = "Find by id",
            responseCode = "200")
    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<SpecialistCv> findById(@PathVariable UUID id) throws NotFoundValidationException {
        return specialistCvService.findOne(id);
    }

    @ApiResponse(description = "Update a experience specialist cv",
            responseCode = "200")
    @PutMapping(value = "experience",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Boolean> updateExperience(@Valid @RequestBody SpecialistExperienceUpdateDto specialistExperience)
            throws NotFoundValidationException, CustomValidationException {
        return specialistCvService.updateExperience(specialistExperience);
    }
}
