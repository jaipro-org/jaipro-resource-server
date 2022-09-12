package com.bindord.eureka.resourceserver.generic;

import com.bindord.eureka.resourceserver.advice.CustomValidationException;
import com.bindord.eureka.resourceserver.advice.NotFoundValidationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BaseService<T, V, X, Y> {

    Mono<T> save(X entity) throws NotFoundValidationException, CustomValidationException;

    Mono<T> update(Y entity) throws NotFoundValidationException, CustomValidationException;

    Mono<T> findOne(V id) throws NotFoundValidationException;

    void delete(V id);

    Mono<Void> delete();

    Flux<T> findAll();

}
