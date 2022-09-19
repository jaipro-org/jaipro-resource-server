package com.bindord.eureka.resourceserver.domain.specialist.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;


@Setter
@Getter
public class WorkLocationDto {

    @NotNull
    private UUID specialistId;

    @NotNull
    @Min(1)
    private Integer districtId;

    @NotBlank
    private String countryId;

}
