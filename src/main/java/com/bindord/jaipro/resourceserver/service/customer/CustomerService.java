package com.bindord.jaipro.resourceserver.service.customer;

import com.bindord.jaipro.resourceserver.domain.customer.Customer;
import com.bindord.jaipro.resourceserver.domain.customer.dto.CustomerDto;
import com.bindord.jaipro.resourceserver.domain.customer.dto.CustomerInformationUpdateDto;
import com.bindord.jaipro.resourceserver.domain.customer.dto.CustomerLocationUpdateDto;
import com.bindord.jaipro.resourceserver.domain.customer.dto.CustomerPasswordUpdateDto;
import com.bindord.jaipro.resourceserver.domain.customer.dto.CustomerUpdateDto;
import com.bindord.jaipro.resourceserver.generic.BaseService;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface CustomerService extends BaseService<Customer, UUID, CustomerDto, CustomerUpdateDto> {

    Flux<Customer> findAllNative();

    Mono<Customer> updateAbout(CustomerInformationUpdateDto entity);
    Mono<Boolean> updateLocation(CustomerLocationUpdateDto entity);

    Mono<Boolean> updatePassword(CustomerPasswordUpdateDto entity);
}
