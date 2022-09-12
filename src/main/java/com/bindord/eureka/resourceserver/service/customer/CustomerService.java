package com.bindord.eureka.resourceserver.service.customer;

import com.bindord.eureka.resourceserver.domain.customer.Customer;
import com.bindord.eureka.resourceserver.domain.customer.dto.CustomerDto;
import com.bindord.eureka.resourceserver.domain.customer.dto.CustomerUpdateDto;
import com.bindord.eureka.resourceserver.generic.BaseService;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface CustomerService extends BaseService<Customer, UUID, CustomerDto, CustomerUpdateDto> {

    Flux<Customer> findAllNative();
}
