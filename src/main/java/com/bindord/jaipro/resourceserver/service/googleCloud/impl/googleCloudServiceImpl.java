package com.bindord.jaipro.resourceserver.service.googleCloud.impl;

import com.bindord.jaipro.resourceserver.service.googleCloud.googleCloudService;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class googleCloudServiceImpl implements googleCloudService {

    @Autowired
    private Storage storage;

    @Override
    public boolean saveFile(File file, String ruta) {
        BlobId id = BlobId.of("jaipro", file.getName());
        BlobInfo info = BlobInfo.newBuilder(id).build();
        byte[] bytes = new byte[(int) file.length()];
        storage.create(info, bytes);

        return true;
    }
}
