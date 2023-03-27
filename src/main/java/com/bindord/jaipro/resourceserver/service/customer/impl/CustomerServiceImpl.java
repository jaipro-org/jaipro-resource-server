package com.bindord.jaipro.resourceserver.service.customer.impl;

import com.bindord.jaipro.resourceserver.advice.CustomValidationException;
import com.bindord.jaipro.resourceserver.advice.NotFoundValidationException;
import com.bindord.jaipro.resourceserver.domain.customer.Customer;
import com.bindord.jaipro.resourceserver.domain.customer.dto.CustomerDto;
import com.bindord.jaipro.resourceserver.domain.customer.dto.CustomerInformationUpdateDto;
import com.bindord.jaipro.resourceserver.domain.customer.dto.CustomerLocationUpdateDto;
import com.bindord.jaipro.resourceserver.domain.customer.dto.CustomerPasswordUpdateDto;
import com.bindord.jaipro.resourceserver.domain.customer.dto.CustomerUpdateDto;
import com.bindord.jaipro.resourceserver.repository.CustomerRepository;
import com.bindord.jaipro.resourceserver.service.customer.CustomerService;
import io.r2dbc.postgresql.codec.Json;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static com.bindord.jaipro.resourceserver.utils.Utilitarios.convertJSONtoString;
import static com.bindord.jaipro.resourceserver.utils.Utilitarios.getNullPropertyNames;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    @Override
    public Mono<Customer> save(CustomerDto entity) throws NotFoundValidationException, CustomValidationException {
        return repository.save(convertToEntityForNewCase(entity));
    }

    @Override
    public Mono<Customer> update(CustomerUpdateDto entity) throws NotFoundValidationException, CustomValidationException {
        Mono<Customer> qCustomer = repository.findById(entity.getId());
        return qCustomer.flatMap(qCus -> repository.save(convertToEntity(entity, qCus)));
    }

    @Override
    public Mono<Customer> findOne(UUID id) throws NotFoundValidationException {
        return repository.findById(id);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public Mono<Void> delete() {
        return repository.deleteAll();
    }

    @Override
    public Flux<Customer> findAll() {
        return repository.findAll();
    }

    @Override
    public Flux<Customer> findAllNative() {
        return repository.findAll();
    }

    @Override
    public Mono<Customer> updateAbout(CustomerInformationUpdateDto entity) {
        Mono<Customer> qCustomer = repository.findById(entity.getId());
        return qCustomer.flatMap(qCus -> {
            repository.save(convertToEntity(entity, qCus));
            return Mono.just(qCus);
        }).doOnError(x -> Mono.error(x));
    }

    @Override
    public Mono<Boolean> updateLocation(CustomerLocationUpdateDto entity) {
        Mono<Customer> qCustomer = repository.findById(entity.getId());
        return qCustomer.flatMap(qCus -> {
            repository.save(convertToEntity(entity, qCus));
            return Mono.just(true);
        }).doOnError(x -> Mono.error(x));
    }

    @Override
    public Mono<Boolean> updatePassword(CustomerPasswordUpdateDto entity) {
        Mono<Customer> qCustomer = repository.findById(entity.getId());
        return qCustomer.flatMap(qCus -> {
            try {
                repository.save(qCus);
                return Mono.just(true);
            } catch (Exception ex) {
                return Mono.just(false);
            }
        });
    }


    private Customer convertToEntity(CustomerUpdateDto obj, Customer customer) {
        BeanUtils.copyProperties(obj, customer, getNullPropertyNames(obj));
        customer.setProfilePhoto(
                Json.of(
                        convertJSONtoString(obj.getProfilePhoto())
                )
        );
        return customer;
    }

    private Customer convertToEntity(CustomerLocationUpdateDto obj, Customer customer) {
        BeanUtils.copyProperties(obj, customer, getNullPropertyNames(obj));
        return customer;
    }

    private Customer convertToEntity(CustomerInformationUpdateDto obj, Customer customer) {
        BeanUtils.copyProperties(obj, customer, getNullPropertyNames(obj));
        return customer;
    }

    private Customer convertToEntityForNewCase(CustomerDto obj) {
        var customer = new Customer();
        BeanUtils.copyProperties(obj, customer);
        customer.setProfilePhoto(
                Json.of(
                        convertJSONtoString(obj.getProfilePhoto())
                )
        );
        customer.setNew(true);
        return customer;
    }
}
