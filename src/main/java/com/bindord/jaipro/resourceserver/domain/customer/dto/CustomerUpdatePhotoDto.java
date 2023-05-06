package com.bindord.jaipro.resourceserver.domain.customer.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.codec.multipart.FilePart;

@Setter
@Getter
public class CustomerUpdatePhotoDto {

    private String id;

    private FilePart file;
}
