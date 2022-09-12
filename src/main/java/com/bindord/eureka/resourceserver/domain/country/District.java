package com.bindord.eureka.resourceserver.domain.country;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@Table
public class District {

    @Id
    @Column(value = "DistrictId")
    private Integer districtId;

    @Size(min = 2, max = 50)
    @Column
    private String name;

    @Min(1)
    @Max(10)
    @Column
    private Integer zone;

    @Schema(description = "Could be ubigeo code for Peru or another similar code for other countries")
    @NotBlank
    @Size(min = 3, max = 9)
    @Column
    private String numericCode;

    @Column
    private Integer countryId;

}
