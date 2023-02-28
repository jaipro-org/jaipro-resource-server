package com.bindord.jaipro.resourceserver.service.googleCloud.impl;

import com.bindord.jaipro.resourceserver.service.googleCloud.GoogleCloudService;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@AllArgsConstructor
@Service
public class GoogleCloudServiceImpl implements GoogleCloudService {

    //@Value("${spring.gcp_test.storage.path-credentials}")
    private final String PATH_CREDENTIALS = "C:/Users/GIOVANNY/AppData/Roaming/gcloud/application_default_credentials.json";

    //@Value("${spring.gcp_test.storage.project-id}")
    private final String PROJECT_ID = "ancient-lattice-373518";

    //@Value("${spring.gcp_test.storage.save}")
    private final String PATH_SAVE = "jaipro";

    //@Value("${spring.gcp_test.storage.bucket}")
    private final String BUCKET = "save";

    //@Value("${spring.gcp_test.storage.url-autenticada}")
    private final String URL_AUTENTICADA = "url-autenticada";


    @Override
    public String saveFile(byte[] file, String fileName) {
        Storage storage = getStorage();

        String path = PATH_SAVE.concat(fileName);
        BlobId id = BlobId.of(BUCKET, path);
        BlobInfo info = BlobInfo.newBuilder(id).build();
        storage.create(info, file);

        return URL_AUTENTICADA.concat(fileName);
    }

    private Storage getStorage(){
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
