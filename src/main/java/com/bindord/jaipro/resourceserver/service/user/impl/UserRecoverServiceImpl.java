package com.bindord.jaipro.resourceserver.service.user.impl;

import com.bindord.jaipro.resourceserver.advice.CustomValidationException;
import com.bindord.jaipro.resourceserver.advice.NotFoundValidationException;
import com.bindord.jaipro.resourceserver.domain.user.UserRecover;
import com.bindord.jaipro.resourceserver.domain.user.dto.UserRecoverDto;
import com.bindord.jaipro.resourceserver.domain.user.dto.UserRecoverUpdateDto;
import com.bindord.jaipro.resourceserver.repository.UserRecoverRepository;
import com.bindord.jaipro.resourceserver.service.user.UserRecoverService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.bindord.jaipro.resourceserver.utils.Constants.ERROR_USER_RECOVER_TICKET_EXPIRED_OR_USED;
import static com.bindord.jaipro.resourceserver.utils.Constants.ERROR_USER_RECOVER_TICKET_NOT_FOUND;
import static com.bindord.jaipro.resourceserver.utils.Utilitarios.getNullPropertyNames;
import static java.time.LocalDateTime.now;

@AllArgsConstructor
@Service
public class UserRecoverServiceImpl implements UserRecoverService {

    private final UserRecoverRepository repository;

    @Override
    public Mono<UserRecover> save(UserRecoverDto entity) throws NotFoundValidationException, CustomValidationException {
        return repository.save(convertToEntityForNewCase(entity));
    }

    @Override
    public Mono<UserRecover> update(UserRecoverUpdateDto entity) throws NotFoundValidationException, CustomValidationException {
        Mono<UserRecover> qUserRecover = repository.findById(entity.getId());
        return qUserRecover.flatMap(qCus -> repository.save(convertToEntity(entity, qCus)));
    }

    @Override
    public Mono<UserRecover> findOne(UUID id) throws NotFoundValidationException {
        return repository.findById(id);
    }

    @Override
    public Mono<Void> delete(UUID id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono<Void> delete() {
        return repository.deleteAll();
    }

    @Override
    public Flux<UserRecover> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Void> validateUserRecoverTicket(UUID id, UUID userId) {
        return repository.findByIdAndUserId(id, userId)
                .switchIfEmpty(Mono.error(new CustomValidationException(ERROR_USER_RECOVER_TICKET_NOT_FOUND)))
                .filter(qUseRecover -> now().isBefore(qUseRecover.getLimitDate()) && !qUseRecover.getFlagRecover())
                .switchIfEmpty(Mono.error(new CustomValidationException(ERROR_USER_RECOVER_TICKET_EXPIRED_OR_USED)))
                .then();
    }

    private UserRecover convertToEntity(UserRecoverUpdateDto obj, UserRecover userRecover) {
        BeanUtils.copyProperties(obj, userRecover, getNullPropertyNames(obj));
        return userRecover;
    }

    private UserRecover convertToEntityForNewCase(UserRecoverDto obj) {
        var userRecover = new UserRecover();
        BeanUtils.copyProperties(obj, userRecover);

        userRecover.setLimitDate(now().plusDays(1));
        userRecover.setVerificationCode(UUID.randomUUID().toString());
        userRecover.setFlagRecover(false);

        userRecover.setNew(true);
        return userRecover;
    }
}
