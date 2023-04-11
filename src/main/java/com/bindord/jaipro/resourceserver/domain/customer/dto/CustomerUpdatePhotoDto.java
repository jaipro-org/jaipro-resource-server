package com.bindord.jaipro.resourceserver.domain.customer.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.codec.multipart.FilePart;

import java.util.UUID;

@Setter
@Getter
public class CustomerUpdatePhotoDto {

    private UUID id;

    private FilePart file;
}
