package com.bindord.jaipro.resourceserver.controller;

import com.bindord.jaipro.resourceserver.advice.CustomValidationException;
import com.bindord.jaipro.resourceserver.advice.NotFoundValidationException;
import com.bindord.jaipro.resourceserver.domain.notification.Notification;
import com.bindord.jaipro.resourceserver.domain.notification.dto.NotificationDto;
import com.bindord.jaipro.resourceserver.domain.notification.dto.NotificationUpdateDto;
import com.bindord.jaipro.resourceserver.service.notification.NotificationService;
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
@RequestMapping("${service.ingress.context-path}/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @ApiResponse(description = "Persist a notification",
            responseCode = "200")
    @PostMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Notification> save(@Valid @RequestBody NotificationDto notification)
            throws NotFoundValidationException, CustomValidationException {
        return notificationService.save(notification);
    }

    @ApiResponse(description = "Update a notification",
            responseCode = "200")
    @PutMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Notification> update(@Valid @RequestBody NotificationUpdateDto notification)
            throws NotFoundValidationException, CustomValidationException {
        return notificationService.update(notification);
    }

    @ApiResponse(description = "List notifications",
            responseCode = "200")
    @GetMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Flux<Notification> findAll() {
        return notificationService.findAll();
    }
}
