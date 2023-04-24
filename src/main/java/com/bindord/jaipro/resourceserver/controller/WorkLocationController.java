package com.bindord.jaipro.resourceserver.controller;

import com.bindord.jaipro.resourceserver.advice.CustomValidationException;
import com.bindord.jaipro.resourceserver.advice.NotFoundValidationException;
import com.bindord.jaipro.resourceserver.domain.specialist.WorkLocation;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.WorkLocationDto;
import com.bindord.jaipro.resourceserver.service.specialist.WorkLocationService;
import com.bindord.jaipro.resourceserver.validator.Validator;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
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
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("${service.ingress.context-path}/work-location")
@Validated
public class WorkLocationController {

    private final Validator validator;

    private final WorkLocationService workLocationService;

    @ApiResponse(description = "Persist a workLocation",
            responseCode = "200")
    @PostMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<WorkLocation> save(@Valid @RequestBody WorkLocationDto workLocation)
            throws NotFoundValidationException, CustomValidationException {
        return workLocationService.save(workLocation);
    }

    @ApiResponse(description = "Persist many workLocations",
            responseCode = "200")
    @PostMapping(value = "/list",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Flux<WorkLocation> saveAll(@RequestBody List<@Valid WorkLocation> workLocation) {
        return workLocationService.saveAll(workLocation);
    }

    @ApiResponse(description = "Update a workLocation",
            responseCode = "200")
    @PutMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<WorkLocation> update(@Valid @RequestBody WorkLocationDto workLocation)
            throws NotFoundValidationException, CustomValidationException {
        return workLocationService.update(workLocation);
    }

    @ApiResponse(description = "List workLocations",
            responseCode = "200")
    @GetMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Flux<WorkLocation> findAll() {
        return workLocationService.findAll();
    }

    @ApiResponse(description = "FindAll by SpecialistId",
            responseCode = "200")
    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Flux<WorkLocation> findAllBySpecialistId(@PathVariable UUID id) throws NotFoundValidationException {
        return workLocationService.findAllBySpecialistId(id);
    }

    @ApiResponse(description = "delete work location by id",
            responseCode = "200")
    @DeleteMapping(value = "/{specialistId}/{districtId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Void> deleteBySpecialistId(@PathVariable UUID specialistId, @PathVariable int districtId) throws NotFoundValidationException {
        return workLocationService.deleteWorkLocationBySpecialistId(specialistId, districtId);
    }
}
