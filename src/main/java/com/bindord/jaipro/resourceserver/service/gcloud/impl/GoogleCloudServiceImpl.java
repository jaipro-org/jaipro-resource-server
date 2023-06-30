package com.bindord.jaipro.resourceserver.service.gcloud.impl;

import com.bindord.jaipro.resourceserver.advice.CustomValidationException;
import com.bindord.jaipro.resourceserver.configuration.props.GcpProperties;
import com.bindord.jaipro.resourceserver.service.gcloud.GoogleCloudService;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

import static com.bindord.jaipro.resourceserver.utils.Constants.CUSTOMER_PHOTO_PATH;
import static com.bindord.jaipro.resourceserver.utils.Constants.ERROR_IMAGE_NOT_FOUND_IN_STORAGE;
import static com.bindord.jaipro.resourceserver.utils.Constants.SPECIALIST_GALLERY_PATH;
import static com.bindord.jaipro.resourceserver.utils.Constants.SPECIALIST_PHOTO_PATH;

@Service
public class GoogleCloudServiceImpl implements GoogleCloudService {

    @Value("${spring.gcp.storage.url-autenticada}")
    private String URL_AUTENTICADA;

    @Value("${spring.gcp.storage.encoded-key}")
    private String KEY_ENCODED;

    @Autowired
    private GcpProperties gcpProperties;

    @Override
    public Mono<String> saveCustomerPhoto(byte[] file, String customerId, String extension) {

        String path = CUSTOMER_PHOTO_PATH
                .replace("[FILENAME]", customerId)
                .replace("[EXTENSION]", extension);

        return SaveFile(file, path);
    }

    @Override
    public Mono<String> saveSpecialistPhoto(byte[] file, UUID specialistId, String extension) {

        String specialistIdStr = specialistId.toString();
        String path = SPECIALIST_PHOTO_PATH
                .replace("[ID]", specialistIdStr)
                .replace("[FILENAME]", specialistIdStr)
                .replace("[EXTENSION]", extension);

        return SaveFile(file, path);
    }

    @Override
    public Mono<String> saveSpecialistGallery(byte[] file, UUID specialistId, String fileName) {
        int index = fileName.lastIndexOf(".");
        String extension = fileName.substring(index);
        String path = SPECIALIST_GALLERY_PATH
                .replace("[ID]", specialistId.toString())
                .replace("[FILENAME]", UUID.randomUUID() + extension);

        return SaveFile(file, path);
    }

    @Override
    public Mono<Void> removeCustomerPhoto(String customerId, String extension) {
        String path = CUSTOMER_PHOTO_PATH
                .replace("[FILENAME]", customerId)
                .replace("[EXTENSION]", extension);

        return removeFile(path);
    }

    @Override
    public Mono<Void> removeImageGallery(UUID specialistId, String photoName) {
        String path = SPECIALIST_GALLERY_PATH
                .replace("[ID]", specialistId.toString())
                .replace("[FILENAME]", photoName);

        return removeFile(path);
    }

    @Override
    public Mono<Void> removeSpecialistPhoto(String specialistId, String extension) {
        String path = SPECIALIST_PHOTO_PATH
                .replace("[ID]", specialistId)
                .replace("[FILENAME]", specialistId)
                .replace("[EXTENSION]", extension);

        return removeFile(path);
    }

    private Mono<String> SaveFile(byte[] file, String path) {
        BlobId blobId = BlobId.of(gcpProperties.getStorageBucket(), path);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

        return getStorage()
                .map(storage -> storage.create(blobInfo, file))
                .then(Mono.just(URL_AUTENTICADA.concat(path)));
    }

    private Mono<Void> removeFile(String path) {
        BlobId blobId = BlobId.of(gcpProperties.getStorageBucket(), path);
        return getStorage()
                .map(storage -> storage.delete(blobId))
                .flatMap(deleted -> {
                    if (deleted) {
                        return Mono.empty();
                    }
                    return Mono.error(new CustomValidationException(ERROR_IMAGE_NOT_FOUND_IN_STORAGE));
                });
    }

    private Mono<Storage> getStorage() {
        try {
            var arrayCredentials = new ByteArrayInputStream(Base64.getDecoder().decode(KEY_ENCODED));
            Credentials credentials = GoogleCredentials.fromStream(arrayCredentials);
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials)
                    .setProjectId(gcpProperties.getProjectId()).build().getService();

            return Mono.just(storage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
