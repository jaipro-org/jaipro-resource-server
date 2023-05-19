package com.bindord.jaipro.resourceserver.domain.specialist.dto;

import com.bindord.jaipro.resourceserver.validation.ExtendedEmailValidator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.UUID;

@Setter
@Getter
public class SpecialistDto {

    private UUID id;

    @NotBlank
    @Size(min = 2, max = 36)
    private String name;

    @NotBlank
    @Size(min = 2, max = 36)
    private String lastName;

    @Min(value = 0)
    private Integer identity;

    @Min(value = 1)
    @Max(value = 2)
    private Integer gender;

    @NotBlank
    @Size(min = 2, max = Byte.MAX_VALUE)
    private String address;

    @ExtendedEmailValidator
    @NotBlank
    @Size(min = 7, max = 60)
    private String email;

    @NotBlank
    @Size(min = 8, max = 12)
    private String document;

    @NotBlank
    @Size(min = 9, max = 15)
    private String phone;

    @NotEmpty
    @Size(min = 9, max = 15)
    private String secondaryPhone;

    @NotBlank
    @Size(min = 7, max = Byte.MAX_VALUE)
    private String publicUrl;

    private boolean verified;

    @Schema(example = "1")
    private Integer districtId;

}
