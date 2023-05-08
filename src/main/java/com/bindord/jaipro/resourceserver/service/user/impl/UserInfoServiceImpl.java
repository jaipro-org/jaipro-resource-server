package com.bindord.jaipro.resourceserver.service.user.impl;

import com.bindord.jaipro.resourceserver.advice.CustomValidationException;
import com.bindord.jaipro.resourceserver.advice.NotFoundValidationException;
import com.bindord.jaipro.resourceserver.domain.user.UserInfo;
import com.bindord.jaipro.resourceserver.domain.user.dto.UserInfoDto;
import com.bindord.jaipro.resourceserver.domain.user.dto.UserInfoUpdateDto;
import com.bindord.jaipro.resourceserver.repository.UserInfoRepository;
import com.bindord.jaipro.resourceserver.service.user.UserInfoService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.bindord.jaipro.resourceserver.utils.Utilitarios.getNullPropertyNames;

@AllArgsConstructor
@Service
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository repository;

    @Override
    public Mono<UserInfo> save(UserInfoDto entity) throws NotFoundValidationException, CustomValidationException {
        return repository.save(convertToEntityForNewCase(entity));
    }

    @Override
    public Mono<UserInfo> update(UserInfoUpdateDto entity) throws NotFoundValidationException, CustomValidationException {
        Mono<UserInfo> qUserInfo = repository.findById(entity.getId());
        return qUserInfo.flatMap(qCus -> repository.save(convertToEntity(entity, qCus)));
    }

    @Override
    public Mono<UserInfo> findOne(UUID id) throws NotFoundValidationException {
        return repository.findById(id).switchIfEmpty(Mono.error(new NotFoundValidationException("404")));
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
    public Flux<UserInfo> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<UserInfo> findByEmail(String email) {
        return repository.findByEmail(email).switchIfEmpty(Mono.error(new NotFoundValidationException("404")));
    }

    @SneakyThrows
    private UserInfo convertToEntity(UserInfoUpdateDto obj, UserInfo userInfo) {
        BeanUtils.copyProperties(obj, userInfo, getNullPropertyNames(obj));
        return userInfo;
    }

    private UserInfo convertToEntityForNewCase(UserInfoDto obj) {
        var userInfo = new UserInfo();
        BeanUtils.copyProperties(obj, userInfo);
        userInfo.setNew(true);
        return userInfo;
    }
}
