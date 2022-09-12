package com.bindord.eureka.resourceserver.generic;

public abstract class BaseServiceImpl<T> {

    protected T repository;
    public BaseServiceImpl() {}
    public BaseServiceImpl(T repository) {
        this.repository = repository;
    }
}
