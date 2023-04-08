package com.bindord.jaipro.resourceserver.controller;

import com.bindord.jaipro.resourceserver.advice.CustomValidationException;
import com.bindord.jaipro.resourceserver.advice.NotFoundValidationException;
import com.bindord.jaipro.resourceserver.domain.customer.Customer;
import com.bindord.jaipro.resourceserver.domain.customer.dto.CustomerDto;
import com.bindord.jaipro.resourceserver.domain.customer.dto.CustomerInformationUpdateDto;
import com.bindord.jaipro.resourceserver.domain.customer.dto.CustomerLocationUpdateDto;
import com.bindord.jaipro.resourceserver.domain.customer.dto.CustomerPasswordUpdateDto;
import com.bindord.jaipro.resourceserver.domain.customer.dto.CustomerUpdateDto;
import com.bindord.jaipro.resourceserver.domain.customer.dto.CustomerUpdatePhotoDto;
import com.bindord.jaipro.resourceserver.domain.specialist.Specialist;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistUpdateDto;
import com.bindord.jaipro.resourceserver.service.customer.CustomerService;
import com.bindord.jaipro.resourceserver.service.gcloud.GoogleCloudService;
import com.bindord.jaipro.resourceserver.validator.Validator;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("${service.ingress.context-path}/customer")
public class CustomerController {

    private final Validator validator;

    private final CustomerService customerService;
    private final GoogleCloudService googleCloudService;

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

    @PreAuthorize("hasRole('UMA_AUTHORIZATION')")
    @ApiResponse(description = "Find by id",
            responseCode = "200")
    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Customer> findById(@PathVariable UUID id) throws NotFoundValidationException {
        return customerService.findOne(id);
    }

    @ApiResponse(description = "Update a customer about",
            responseCode = "200")
    @PutMapping(value = "/updateAbout",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Customer> updateAbout(@Valid @RequestBody CustomerInformationUpdateDto customer)
            throws NotFoundValidationException, CustomValidationException {
        return customerService.updateAbout(customer);
    }

    @ApiResponse(description = "Update a customer location",
            responseCode = "200")
    @PutMapping(value = "/updateLocation",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Boolean> updateLocation(@Valid @RequestBody CustomerLocationUpdateDto customer)
            throws NotFoundValidationException, CustomValidationException {
        return customerService.updateLocation(customer);
    }

    @ApiResponse(description = "Update a customer password",
            responseCode = "200")
    @PutMapping(value = "/updatePassword",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Boolean> updatePassword(@Valid @RequestBody CustomerPasswordUpdateDto customer)
            throws NotFoundValidationException, CustomValidationException {
        return customerService.updatePassword(customer);
    }

    /*@ApiResponse(description = "Get customer information",
            responseCode = "200")
    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Customer> GetInformationById(@PathVariable UUID id) throws NotFoundValidationException {
        return customerService.findOne(id);
    }*/

    @ApiResponse(description = "Update a customer photo",
            responseCode = "200")
    @PutMapping(value = "/updatePhoto",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<String> updatePhoto(@Valid @RequestBody CustomerUpdatePhotoDto customer)
            throws NotFoundValidationException, CustomValidationException {

        Mono<String> monoImage = uploadPhotoFile(customer.getFile(), customer.getId());
        return  monoImage
                    .map(url -> customerService.updatePhoto(customer, url))
                    .then(monoImage);
    }

    private Mono<String> uploadPhotoFile(@RequestPart("file") FilePart file, UUID customerId){

        return DataBufferUtils.join(file.content())
                    .map(dataBuffer -> dataBuffer.asByteBuffer().array())
                    .map(bytes -> googleCloudService.saveCustomerPhoto(bytes, customerId))
                    .flatMap(urlPath -> urlPath);
    }
}
