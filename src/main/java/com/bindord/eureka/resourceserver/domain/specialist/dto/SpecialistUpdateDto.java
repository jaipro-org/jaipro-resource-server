package com.bindord.eureka.resourceserver.domain.specialist.dto;

import com.bindord.eureka.resourceserver.domain.json.Rating;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class SpecialistUpdateDto {

    private UUID id;

    @NotBlank
    @Size(min = 2, max = 36)
    private String name;

    @NotBlank
    @Size(min = 2, max = 36)
    private String lastName;

    @NotBlank
    @Size(min = 2, max = Byte.MAX_VALUE)
    private String address;

    @NotBlank
    @Size(min = 9, max = 15)
    private String phone;

    @Valid
    private List<Rating> ratings;

    @Schema(example = "1")
    private Integer districtId;

}
