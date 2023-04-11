package com.bindord.jaipro.resourceserver.service.gcloud;

import reactor.core.publisher.Mono;

import java.util.UUID;

public interface GoogleCloudService {

    Mono<String> saveCustomerPhoto(byte[] file, UUID customerId);

    Mono<String> saveSpecialistPhoto(byte[] file, UUID specialistId);
}
