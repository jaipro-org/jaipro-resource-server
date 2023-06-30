package com.bindord.jaipro.resourceserver.service.mail.impl;

import com.bindord.jaipro.resourceserver.advice.CustomValidationException;
import com.bindord.jaipro.resourceserver.advice.NotFoundValidationException;
import com.bindord.jaipro.resourceserver.configuration.props.GcpProperties;
import com.bindord.jaipro.resourceserver.domain.mail.Mail;
import com.bindord.jaipro.resourceserver.domain.mail.dto.MailDto;
import com.bindord.jaipro.resourceserver.domain.mail.dto.MailUpdateDto;
import com.bindord.jaipro.resourceserver.domain.mail.dto.SendMailDto;
import com.bindord.jaipro.resourceserver.domain.mail.dto.operation.RecoverPasswordDto;
import com.bindord.jaipro.resourceserver.repository.MailRepository;
import com.bindord.jaipro.resourceserver.service.mail.MailService;
import com.bindord.jaipro.resourceserver.wsc.KeycloakAuthClientConfiguration;
import io.r2dbc.postgresql.codec.Json;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Set;

import static com.bindord.jaipro.resourceserver.utils.Constants.DOMAIN_FRONTEND;
import static com.bindord.jaipro.resourceserver.utils.Enums.Mail.INIT_CAMBIO_PASSWORD;
import static com.bindord.jaipro.resourceserver.utils.Utilitarios.convertJSONtoBase64;
import static com.bindord.jaipro.resourceserver.utils.Utilitarios.convertJSONtoString;
import static com.bindord.jaipro.resourceserver.utils.Utilitarios.getNullPropertyNames;
import static org.springframework.util.CollectionUtils.isEmpty;

@AllArgsConstructor
@Service
public class MailServiceImpl implements MailService {

    private final MailRepository repository;
    private final KeycloakAuthClientConfiguration keycloakAuthClientConfiguration;
    private final GcpProperties gcpProperties;

    @Override
    public Mono<Mail> save(MailDto entity) throws NotFoundValidationException, CustomValidationException {
        return repository.save(convertToEntityForNewCase(entity));
    }

    @Override
    public Mono<Mail> update(MailUpdateDto entity) throws NotFoundValidationException, CustomValidationException {
        Mono<Mail> qMail = repository.findById(entity.getId());
        return qMail.flatMap(qCus -> repository.save(convertToEntity(entity, qCus)));
    }

    @Override
    public Mono<Mail> findOne(Integer id) throws NotFoundValidationException {
        return repository.findById(id);
    }

    @Override
    public Mono<Void> delete(Integer id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono<Void> delete() {
        return repository.deleteAll();
    }

    @Override
    public Flux<Mail> findAll() {
        return repository.findAll();
    }

    private Mail convertToEntity(MailUpdateDto obj, Mail mail) {
        BeanUtils.copyProperties(obj, mail, getNullPropertyNames(obj));
        if (!isEmpty(obj.getInputs())) {
            mail.setInputs(
                    Json.of(
                            convertJSONtoString(obj.getInputs())
                    )
            );
        }
        return mail;
    }

    private Mail convertToEntityForNewCase(MailDto obj) {
        var mail = new Mail();
        BeanUtils.copyProperties(obj, mail);
        if (!isEmpty(obj.getInputs())) {
            mail.setInputs(
                    Json.of(
                            convertJSONtoString(obj.getInputs())
                    )
            );
        }
        mail.setNew(true);
        return mail;
    }

    @SneakyThrows
    @Override
    public Mono<Void> sendRecoverPasswordMail(RecoverPasswordDto recoverPassword) {
        return this.findOne(INIT_CAMBIO_PASSWORD.get())
                .flatMap(mail -> {
                    String body = mail.getBody();
                    body = body.replace("__DOMINIO__", String.format(
                                    DOMAIN_FRONTEND, gcpProperties.getProjectId()))
                            .replace("__BASE64__", convertJSONtoBase64(convertJSONtoString(recoverPassword)));
                    mail.setBody(body);

                    var sendMailDto = new SendMailDto(
                            mail.getSubject(),
                            body,
                            Set.of(recoverPassword.getEmailReceiver()));

                    return keycloakAuthClientConfiguration.init()
                            .post()
                            .uri("/mail/send")
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(Mono.just(sendMailDto), SendMailDto.class)
                            .retrieve()
                            .bodyToMono(Void.class)
                            .subscribeOn(Schedulers.boundedElastic());
                });
    }
}
