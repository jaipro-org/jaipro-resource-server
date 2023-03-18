package com.bindord.jaipro.resourceserver.controller;

import com.bindord.jaipro.resourceserver.advice.CustomValidationException;
import com.bindord.jaipro.resourceserver.advice.NotFoundValidationException;
import com.bindord.jaipro.resourceserver.domain.user.UserInfo;
import com.bindord.jaipro.resourceserver.domain.user.dto.UserInfoDto;
import com.bindord.jaipro.resourceserver.domain.user.dto.UserInfoUpdateDto;
import com.bindord.jaipro.resourceserver.service.user.UserInfoService;
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
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("${service.ingress.context-path}/user-info")
public class UserInfoController {

    private final Validator validator;

    private final UserInfoService userInfoService;

    @ApiResponse(description = "Persist an user info",
            responseCode = "200")
    @PostMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<UserInfo> save(@Valid @RequestBody UserInfoDto userInfo)
            throws NotFoundValidationException, CustomValidationException {
        return userInfoService.save(userInfo);
    }

    @ApiResponse(description = "Update an user info",
            responseCode = "200")
    @PutMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<UserInfo> update(@Valid @RequestBody UserInfoUpdateDto userInfo)
            throws NotFoundValidationException, CustomValidationException {
        return userInfoService.update(userInfo);
    }

    @ApiResponse(description = "List users info",
            responseCode = "200")
    @GetMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Flux<UserInfo> findAll() {
        return userInfoService.findAll();
    }

    @ApiResponse(description = "Find by id",
            responseCode = "200")
    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<UserInfo> findById(@PathVariable UUID id) throws NotFoundValidationException {
        return userInfoService.findOne(id);
    }
}
