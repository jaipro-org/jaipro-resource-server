package com.bindord.jaipro.resourceserver.domain.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Setter
@Getter
public class CustomerLocationUpdateDto {

    @NotBlank
    @Valid
    private UUID id;

    @Size(min = 2, max = Byte.MAX_VALUE)
    private String address;

    @Schema(example = "1")
    @Min(value = 1)
    private Integer districtId;
}
