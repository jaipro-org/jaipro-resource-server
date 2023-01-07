package com.bindord.jaipro.resourceserver.domain.country;

import com.bindord.jaipro.resourceserver.domain.base.BaseDomain;
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
public class District extends BaseDomain {

    @Id
    @Column(value = "district_id")
    private Integer id;

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
    @Size(min = 3, max = 3)
    private String countryId;

}
