package com.bindord.jaipro.resourceserver.utils;

import com.bindord.jaipro.resourceserver.configuration.JacksonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;

import java.beans.FeatureDescriptor;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.stream.Stream;

public class Utilitarios {

    public static final Logger LOGGER = LogManager.getLogger(Utilitarios.class);

    private Utilitarios() {
    }

    public static void createDirectoryStartUp(String basePath, String[] paths) {

        for (int i = 0; i < paths.length; i++) {
            File dirFile = new File(basePath);

            if (!dirFile.exists()) {
                try {
                    dirFile.mkdir();
                } catch (SecurityException se) {
                    LOGGER.info(se.getMessage());
                }
            } else {
                File nuevoFile = new File(basePath + paths[i]);
                if (!nuevoFile.exists()) {
                    try {
                        nuevoFile.mkdir();
                    } catch (SecurityException se) {
                        LOGGER.info(se.getMessage());
                    }
                }
            }
        }
    }

    public static String convertJSONtoString(Object obj) {
        try {
            return JacksonFactory.getObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return StringUtils.EMPTY;
        }
    }

    public static ObjectMapper instanceObjectMapper() {
        return JacksonFactory.getObjectMapper();
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }

    public static String customResponse(String code, String domainPk) {
        return "{\"code\": \"" + code + "\", \"id\": \"" + domainPk + "\"}";
    }

    public static String[] filterStringArray(String[] array) {
        array = Arrays.stream(array)
                .filter(Objects::nonNull)
                .toArray(String[]::new);
        return array;
    }

    public static byte[] getBytestoFilePart(FilePart file) {
        return DataBufferUtils.join(file.content())
                .map(dataBuffer -> dataBuffer.asByteBuffer().array())
                .map(x -> x).block();
    }

    public static String convertJSONtoBase64(String json) {
        return new String(Base64.getUrlEncoder().encode(json.getBytes(StandardCharsets.UTF_8)));
    }
}

