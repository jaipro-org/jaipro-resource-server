package com.bindord.jaipro.resourceserver.controller;

import com.bindord.jaipro.resourceserver.advice.CustomValidationException;
import com.bindord.jaipro.resourceserver.advice.NotFoundValidationException;
import com.bindord.jaipro.resourceserver.domain.mail.Mail;
import com.bindord.jaipro.resourceserver.domain.mail.dto.MailDto;
import com.bindord.jaipro.resourceserver.domain.mail.dto.MailUpdateDto;
import com.bindord.jaipro.resourceserver.domain.mail.dto.operation.RecoverPasswordDto;
import com.bindord.jaipro.resourceserver.service.mail.MailService;
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
@RequestMapping("${service.ingress.context-path}/mail")
public class MailController {

    private final MailService mailService;

    @ApiResponse(description = "Persist a mail",
            responseCode = "200")
    @PostMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Mail> save(@Valid @RequestBody MailDto mail)
            throws NotFoundValidationException, CustomValidationException {
        return mailService.save(mail);
    }

    @ApiResponse(description = "Update a mail",
            responseCode = "200")
    @PutMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Mail> update(@Valid @RequestBody MailUpdateDto mail)
            throws NotFoundValidationException, CustomValidationException {
        return mailService.update(mail);
    }

    @ApiResponse(description = "List mails",
            responseCode = "200")
    @GetMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Flux<Mail> findAll() {
        return mailService.findAll();
    }

    @ApiResponse(description = "Send email to init recover password process",
            responseCode = "200")
    @PostMapping(value = "/send/init-recover-password",
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Void> initRecoverPassword(@Valid @RequestBody RecoverPasswordDto userRecoverDto) {
        return mailService.sendRecoverPasswordMail(userRecoverDto);
    }

}
