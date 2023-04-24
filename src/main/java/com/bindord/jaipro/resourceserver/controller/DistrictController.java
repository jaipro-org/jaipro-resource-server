package com.bindord.jaipro.resourceserver.controller;

import com.bindord.jaipro.resourceserver.advice.CustomValidationException;
import com.bindord.jaipro.resourceserver.advice.NotFoundValidationException;
import com.bindord.jaipro.resourceserver.domain.country.District;
import com.bindord.jaipro.resourceserver.domain.country.dto.DistrictDto;
import com.bindord.jaipro.resourceserver.domain.country.dto.DistrictUpdateDto;
import com.bindord.jaipro.resourceserver.service.country.DistrictService;
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
@RequestMapping("${service.ingress.context-path}/district")
public class DistrictController {

    private final Validator validator;

    private final DistrictService districtService;

    @ApiResponse(description = "Persist a district",
            responseCode = "200")
    @PostMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<District> save(@Valid @RequestBody DistrictDto district)
            throws NotFoundValidationException, CustomValidationException {
        return districtService.save(district);
    }

    @ApiResponse(description = "Update a district",
            responseCode = "200")
    @PutMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<District> update(@Valid @RequestBody DistrictUpdateDto district)
            throws NotFoundValidationException, CustomValidationException {
        return districtService.update(district);
    }

    @ApiResponse(description = "List districts",
            responseCode = "200")
    @GetMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Flux<District> findAll() {
        return districtService.findAll();
    }

}
