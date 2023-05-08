package com.bindord.jaipro.resourceserver.service.user;

import com.bindord.jaipro.resourceserver.domain.user.UserRecover;
import com.bindord.jaipro.resourceserver.domain.user.dto.UserRecoverDto;
import com.bindord.jaipro.resourceserver.domain.user.dto.UserRecoverUpdateDto;
import com.bindord.jaipro.resourceserver.generic.BaseService;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRecoverService extends BaseService<UserRecover, UUID, UserRecoverDto, UserRecoverUpdateDto> {

    Mono<Void> validateUserRecoverTicket(UUID id, UUID userId);
}
