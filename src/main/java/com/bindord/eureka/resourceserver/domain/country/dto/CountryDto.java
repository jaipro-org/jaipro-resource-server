package com.bindord.eureka.resourceserver.domain.country.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
public class CountryDto {

    @Size(min = 3, max = 3)
    @Schema(description = "Codes from ISO 3166-1, Alpha-3 code")
    private String id;

    @Size(min = 2, max = 60)
    private String name;

    @Schema(description = "Numeric code from ISO 3166-1")
    @NotBlank
    @Size(min = 3, max = 3)
    private String numericCode;

    public CountryDto() {
    }

}
