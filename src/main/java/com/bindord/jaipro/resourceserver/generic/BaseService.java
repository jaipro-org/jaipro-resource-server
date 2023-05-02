package com.bindord.jaipro.resourceserver.generic;

import com.bindord.jaipro.resourceserver.advice.CustomValidationException;
import com.bindord.jaipro.resourceserver.advice.NotFoundValidationException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BaseService<T, V, X, Y> {

    Mono<T> save(X entity) throws NotFoundValidationException, CustomValidationException;

    Mono<T> update(Y entity) throws NotFoundValidationException, CustomValidationException;

    Mono<T> findOne(V id) throws NotFoundValidationException;

    Mono<Void> delete(V id);

    Mono<Void> delete();

    Flux<T> findAll();

}
