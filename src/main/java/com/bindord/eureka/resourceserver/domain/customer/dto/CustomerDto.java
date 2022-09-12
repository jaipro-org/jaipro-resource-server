package com.bindord.eureka.resourceserver.domain.customer.dto;

import com.bindord.eureka.resourceserver.domain.json.Photo;
import com.bindord.eureka.resourceserver.validation.ExtendedEmailValidator;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Setter
@Getter
public class CustomerDto {

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
    @Schema(example = "pedropablo.182@gmail.com")
    private String email;

    @Size(min = 8, max = 12)
    @Schema(example = "44448888")
    private String document;

    @NotBlank
    @Size(min = 9, max = 15)
    private String phone;

    @Valid
    private Photo profilePhoto;

    @NotBlank
    @Size(min = 7, max = Byte.MAX_VALUE)
    private String publicUrl;

    private boolean verifiedEmail;

    private Integer districtId;

}
