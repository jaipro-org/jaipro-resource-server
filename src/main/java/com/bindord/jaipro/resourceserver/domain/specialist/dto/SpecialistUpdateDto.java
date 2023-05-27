package com.bindord.jaipro.resourceserver.domain.specialist.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.http.codec.multipart.FilePart;

import javax.validation.constraints.Size;
import java.util.UUID;

@Setter
@Getter
public class SpecialistUpdateDto {

    private UUID id;

    @Size(min = 2, max = 36)
    private String name;

    @Size(min = 2, max = 36)
    private String lastName;

    @Size(min = 2, max = Byte.MAX_VALUE)
    private String address;

    @Size(min = 9, max = 15)
    private String phone;

    @Size(min = 9, max = 15)
    @Column
    private String secondaryPhone;

    @Schema(example = "1")
    private Integer districtId;

    private String file;

}
