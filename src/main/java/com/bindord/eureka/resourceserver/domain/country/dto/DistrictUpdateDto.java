package com.bindord.eureka.resourceserver.domain.country.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Setter
@Getter
public class DistrictUpdateDto {

    private Integer id;

    @Size(min = 2, max = 50)
    private String name;

    @Min(1)
    @Max(10)
    private Integer zone;

    @Schema(description = "Could be ubigeo code for Peru or another similar code for other countries")
    @Size(min = 3, max = 9)
    private String numericCode;

}
