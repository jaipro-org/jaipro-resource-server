package com.bindord.jaipro.resourceserver.service.customer.impl;

import com.bindord.jaipro.resourceserver.advice.CustomValidationException;
import com.bindord.jaipro.resourceserver.advice.NotFoundValidationException;
import com.bindord.jaipro.resourceserver.domain.customer.Customer;
import com.bindord.jaipro.resourceserver.domain.customer.dto.CustomerDto;
import com.bindord.jaipro.resourceserver.domain.customer.dto.CustomerInformationDto;
import com.bindord.jaipro.resourceserver.domain.customer.dto.CustomerInformationUpdateDto;
import com.bindord.jaipro.resourceserver.domain.customer.dto.CustomerLocationUpdateDto;
import com.bindord.jaipro.resourceserver.domain.customer.dto.CustomerPasswordUpdateDto;
import com.bindord.jaipro.resourceserver.domain.customer.dto.CustomerUpdateDto;
import com.bindord.jaipro.resourceserver.domain.customer.dto.CustomerUpdatePhotoDto;
import com.bindord.jaipro.resourceserver.domain.json.Photo;
import com.bindord.jaipro.resourceserver.repository.CustomerRepository;
import com.bindord.jaipro.resourceserver.service.customer.CustomerService;
import com.fasterxml.jackson.core.type.TypeReference;
import io.r2dbc.postgresql.codec.Json;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.bindord.jaipro.resourceserver.utils.Constants.ERROR_EXPERIENCE_REPEATED;
import static com.bindord.jaipro.resourceserver.utils.Constants.RESOURCE_NOT_FOUND;
import static com.bindord.jaipro.resourceserver.utils.Utilitarios.convertJSONtoString;
import static com.bindord.jaipro.resourceserver.utils.Utilitarios.getNullPropertyNames;
import static com.bindord.jaipro.resourceserver.utils.Utilitarios.instanceObjectMapper;
import static java.time.LocalDateTime.now;
import static java.util.Objects.isNull;

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
    public Mono<Void> delete(UUID id) {
        return repository.deleteById(id);
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
    public Mono<CustomerInformationDto> GetInformation(UUID id) {
        return repository
                .findById(id)
                .map(qCus -> convertToDto(qCus));
    }

    @Override
    public Mono<Customer> updateAbout(CustomerInformationUpdateDto entity) {
        Mono<Customer> qCustomer = repository.findById(entity.getId());
        return qCustomer
                    .flatMap(qCus -> repository.save(convertToEntity(entity, qCus)))
                    .map(cus -> cus);
    }

    @Override
    public Mono<Void> updateLocation(CustomerLocationUpdateDto entity) {
        Mono<Customer> qCustomer = repository.findById(entity.getId());
        return qCustomer
                    .flatMap(qCus -> repository.save(convertToEntity(entity, qCus)))
                    .then(Mono.empty());
    }

    @Override
    public Mono<Void> updatePhoto(String id, String urlSource) {
        UUID idUUID = UUID.fromString(id);
        Mono<Customer> qCustomer = repository.findById(idUUID);
        return qCustomer
                .switchIfEmpty(Mono.error(new CustomValidationException(RESOURCE_NOT_FOUND)))
                .flatMap(qCus -> repository.save(mapperPhoto(id, urlSource, qCus)))
                .then();
    }

    @Override
    public Mono<Void> updatePassword(CustomerPasswordUpdateDto entity) {
        Mono<Customer> qCustomer = repository.findById(entity.getId());
        return qCustomer
                    .flatMap(qCus -> repository.save(qCus))
                    .then(Mono.empty());
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
        if (!isNull(obj.getProfilePhoto())) {
            customer.setProfilePhoto(
                    Json.of(
                            convertJSONtoString(obj.getProfilePhoto())
                    )
            );
        }
        customer.setNew(true);
        return customer;
    }

    @SneakyThrows
    private CustomerInformationDto convertToDto(Customer customer) {
        CustomerInformationDto customerInformationDto = new CustomerInformationDto();
        BeanUtils.copyProperties(customer, customerInformationDto, getNullPropertyNames(customer));

        if (customer.getProfilePhoto() != null) {
            Photo photo = convertJsonToClass(customer.getProfilePhoto());
            customerInformationDto.setAvatar(photo.getUrl());
        }
        return customerInformationDto;
    }

    private Photo convertJsonToClass(Json json) throws IOException {
        var objectMapper = instanceObjectMapper();

        Photo photo = objectMapper.readValue(json.asString(), new TypeReference<Photo>() {});
        return photo;
    }

    private Customer mapperPhoto(String id, String urlSource, Customer entity){
        if(urlSource.isBlank())
            return entity;

        Photo photo = new Photo();
        photo.setName(id);
        photo.setUrl(urlSource);
        photo.setDate(LocalDateTime.now());

        entity.setProfilePhoto(Json.of(convertJSONtoString(photo)));
        return entity;
    }
}
