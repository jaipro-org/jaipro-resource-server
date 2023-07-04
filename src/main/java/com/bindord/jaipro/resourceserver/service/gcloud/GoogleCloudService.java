package com.bindord.jaipro.resourceserver.service.gcloud;

import reactor.core.publisher.Mono;

import java.util.UUID;

public interface GoogleCloudService {

    Mono<String> saveCustomerPhoto(byte[] file, String customerId, String extension);
    Mono<String> saveSpecialistPhoto(byte[] file, UUID specialistId, String extension);
    Mono<String> saveSpecialistGallery(byte[] file, UUID specialistId, String fileName);
    Mono<String> saveServiceRequestGallery(byte[] file, String fileName, UUID customerId, UUID serviceRequestId);
    Mono<Void> removeCustomerPhoto(String customerId, String extension);
    Mono<Void> removeSpecialistPhoto(String specialistId, String extension);
    Mono<Void> removeImageGallery(UUID specialistId, String photoName);
}
