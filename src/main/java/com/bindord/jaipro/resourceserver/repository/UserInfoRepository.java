package com.bindord.jaipro.resourceserver.repository;

import com.bindord.jaipro.resourceserver.domain.user.UserInfo;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface UserInfoRepository extends ReactiveCrudRepository<UserInfo, UUID> {

    Mono<UserInfo> findByEmail(String email);

}
