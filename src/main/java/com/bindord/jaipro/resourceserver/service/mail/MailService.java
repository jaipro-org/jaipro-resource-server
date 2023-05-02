package com.bindord.jaipro.resourceserver.service.mail;

import com.bindord.jaipro.resourceserver.domain.mail.Mail;
import com.bindord.jaipro.resourceserver.domain.mail.dto.MailDto;
import com.bindord.jaipro.resourceserver.domain.mail.dto.MailUpdateDto;
import com.bindord.jaipro.resourceserver.domain.mail.dto.operation.RecoverPasswordDto;
import com.bindord.jaipro.resourceserver.generic.BaseService;
import reactor.core.publisher.Mono;

public interface MailService extends BaseService<Mail, Integer, MailDto, MailUpdateDto> {

    Mono<Void> sendRecoverPasswordMail(RecoverPasswordDto userRecover);
}
