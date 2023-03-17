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

import java.io.FileInputStream;
import java.io.IOException;

@Service
public class GoogleCloudServiceImpl implements GoogleCloudService {

    @Value("${spring.gcp.storage.path-credentials}")
    private String PATH_CREDENTIALS;

    @Value("${spring.gcp.storage.project-id}")
    private String PROJECT_ID;

    @Value("${spring.gcp.storage.save}")
    private String PATH_SAVE;

    @Value("${spring.gcp.storage.bucket}")
    private String BUCKET;
    @Value("${spring.gcp.storage.url-autenticada}")
    private String URL_AUTENTICADA;

    @Override
    public String saveFile(byte[] file, String fileName, String specialistIdStr) {
        Storage storage = getStorage();

        String path = PATH_SAVE.format(specialistIdStr, fileName);
        BlobId id = BlobId.of(BUCKET, path);
        BlobInfo info = BlobInfo.newBuilder(id).build();
        storage.create(info, file);

        return URL_AUTENTICADA.concat(fileName);
    }

    private Storage getStorage() {
        try {
            Credentials credentials = GoogleCredentials.fromStream(new FileInputStream(PATH_CREDENTIALS));
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials)
                    .setProjectId(PROJECT_ID).build().getService();

            return storage;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
