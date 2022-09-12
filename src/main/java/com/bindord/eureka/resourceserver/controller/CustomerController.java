package com.bindord.eureka.resourceserver.controller;

import com.bindord.eureka.resourceserver.advice.CustomValidationException;
import com.bindord.eureka.resourceserver.advice.NotFoundValidationException;
import com.bindord.eureka.resourceserver.domain.customer.Customer;
import com.bindord.eureka.resourceserver.domain.customer.dto.CustomerDto;
import com.bindord.eureka.resourceserver.domain.customer.dto.CustomerUpdateDto;
import com.bindord.eureka.resourceserver.service.customer.CustomerService;
import com.bindord.eureka.resourceserver.validator.Validator;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("${service.ingress.context-path}/customer")
public class CustomerController {

    private final Validator validator;

    private final CustomerService customerService;

    @ApiResponse(description = "Persist a customer",
            responseCode = "200")
    @PostMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Customer> save(@Valid @RequestBody CustomerDto customer)
            throws NotFoundValidationException, CustomValidationException {
        return customerService.save(customer);
    }

    @ApiResponse(description = "Update a customer",
            responseCode = "200")
    @PutMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Customer> update(@Valid @RequestBody CustomerUpdateDto customer)
            throws NotFoundValidationException, CustomValidationException {
        return customerService.update(customer);
    }

    @ApiResponse(description = "List customers",
            responseCode = "200")
    @GetMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Flux<Customer> findAll() {
        return customerService.findAll();
    }

}
