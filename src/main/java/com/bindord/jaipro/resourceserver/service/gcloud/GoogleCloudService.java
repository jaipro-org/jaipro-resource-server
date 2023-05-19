package com.bindord.jaipro.resourceserver.service.gcloud;

import reactor.core.publisher.Mono;

import java.util.UUID;

public interface GoogleCloudService {

    Mono<String> saveCustomerPhoto(byte[] file, String customerId, String extension);

    Mono<String> saveSpecialistPhoto(byte[] file, UUID specialistId, String extension);
    Mono<String> saveSpecialistGallery(byte[] file, UUID specialistId, String fileName);
    Mono<Void> removeCustomerPhoto(String customerId, String extension);
    Mono<Void> removeSpecialistPhoto(String specialistId, String extension);
}
