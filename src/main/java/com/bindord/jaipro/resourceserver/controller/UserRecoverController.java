package com.bindord.jaipro.resourceserver.controller;

import com.bindord.jaipro.resourceserver.advice.CustomValidationException;
import com.bindord.jaipro.resourceserver.advice.NotFoundValidationException;
import com.bindord.jaipro.resourceserver.domain.user.UserRecover;
import com.bindord.jaipro.resourceserver.domain.user.dto.UserRecoverDto;
import com.bindord.jaipro.resourceserver.domain.user.dto.UserRecoverUpdateDto;
import com.bindord.jaipro.resourceserver.service.user.UserRecoverService;
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
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("${service.ingress.context-path}/user-recover")
public class UserRecoverController {

    private final UserRecoverService userRecoverService;

    @ApiResponse(description = "Persist a userRecover",
            responseCode = "200")
    @PostMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<UserRecover> save(@Valid @RequestBody UserRecoverDto userRecover)
            throws NotFoundValidationException, CustomValidationException {
        return userRecoverService.save(userRecover);
    }

    @ApiResponse(description = "Update a userRecover",
            responseCode = "200")
    @PutMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<UserRecover> update(@Valid @RequestBody UserRecoverUpdateDto userRecover)
            throws NotFoundValidationException, CustomValidationException {
        return userRecoverService.update(userRecover);
    }

    @ApiResponse(description = "List userRecovers",
            responseCode = "200")
    @GetMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Flux<UserRecover> findAll() {
        return userRecoverService.findAll();
    }

    @ApiResponse(description = "Validate limit date of ticket to recover password",
            responseCode = "200")
    @GetMapping(value = "/{id}/user/{userId}")
    public Mono<Void> validateUserRecoverTicket(@PathVariable UUID id, @PathVariable UUID userId) {
        return userRecoverService.validateUserRecoverTicket(id, userId);
    }

}
