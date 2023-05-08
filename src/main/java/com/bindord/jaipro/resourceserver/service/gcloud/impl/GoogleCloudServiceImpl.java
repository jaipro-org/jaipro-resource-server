package com.bindord.jaipro.resourceserver.service.gcloud.impl;

import com.bindord.jaipro.resourceserver.service.gcloud.GoogleCloudService;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

import static com.bindord.jaipro.resourceserver.utils.Constants.CUSTOMER_PHOTO_PATH;
import static com.bindord.jaipro.resourceserver.utils.Constants.SPECIALIST_GALLERY_PATH;
import static com.bindord.jaipro.resourceserver.utils.Constants.SPECIALIST_PHOTO_PATH;

@Service
public class GoogleCloudServiceImpl implements GoogleCloudService {

    @Value("${spring.gcp.storage.path-credentials}")
    private String PATH_CREDENTIALS;

    @Value("${spring.gcp.storage.project-id}")
    private String PROJECT_ID;

    @Value("${spring.gcp.storage.bucket}")
    private String BUCKET;
    @Value("${spring.gcp.storage.url-autenticada}")
    private String URL_AUTENTICADA;

    @Override
    public Mono<String> saveCustomerPhoto(byte[] file, String customerId, String extension) {

        String customerIdStr = customerId.toString();
        String path = CUSTOMER_PHOTO_PATH
                            .replace("[FILENAME]", customerIdStr)
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

        String specialistIdStr = specialistId.toString();
        String path = SPECIALIST_GALLERY_PATH
                           /* .replace("[ID]", specialistIdStr)*/
                            .replace("[FILENAME]", fileName);

        return SaveFile(file, path);
    }

    private Mono<String> SaveFile(byte[] file, String path) {
        BlobId blobId = BlobId.of(BUCKET, path);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

        return getStorage()
                    .map(storage -> storage.create(blobInfo, file))
                    .then(Mono.just(URL_AUTENTICADA.concat(path)));
    }

    private Mono<Storage> getStorage() {
        try {
            Credentials credentials = GoogleCredentials.fromStream(new FileInputStream(PATH_CREDENTIALS));
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials)
                    .setProjectId(PROJECT_ID).build().getService();

            return Mono.just(storage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
