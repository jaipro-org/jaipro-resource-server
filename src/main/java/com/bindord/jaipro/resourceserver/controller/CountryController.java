package com.bindord.jaipro.resourceserver.controller;

import com.bindord.jaipro.resourceserver.advice.CustomValidationException;
import com.bindord.jaipro.resourceserver.advice.NotFoundValidationException;
import com.bindord.jaipro.resourceserver.domain.country.Country;
import com.bindord.jaipro.resourceserver.domain.country.dto.CountryDto;
import com.bindord.jaipro.resourceserver.domain.country.dto.CountryUpdateDto;
import com.bindord.jaipro.resourceserver.service.country.CountryService;
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
@RequestMapping("${service.ingress.context-path}/country")
public class CountryController {

    private final CountryService countryService;

    @ApiResponse(description = "Persist a country",
            responseCode = "200")
    @PostMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Country> save(@Valid @RequestBody CountryDto country)
            throws NotFoundValidationException, CustomValidationException {
        return countryService.save(country);
    }

    @ApiResponse(description = "Update a country",
            responseCode = "200")
    @PutMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Country> update(@Valid @RequestBody CountryUpdateDto country)
            throws NotFoundValidationException, CustomValidationException {
        return countryService.update(country);
    }

    @ApiResponse(description = "List countrys",
            responseCode = "200")
    @GetMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Flux<Country> findAll() {
        return countryService.findAll();
    }

}
