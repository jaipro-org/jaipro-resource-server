package com.bindord.jaipro.resourceserver.domain.customer.dto;

import com.bindord.jaipro.resourceserver.domain.json.Photo;
import com.bindord.jaipro.resourceserver.validation.ExtendedEmailValidator;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Setter
@Getter
public class CustomerInformationUpdateDto {

    @NotBlank
    @Valid
    private UUID id;

    @Size(min = 2, max = 36)
    private String name;

    @Size(min = 2, max = 36)
    private String lastName;

    @ExtendedEmailValidator
    @NotBlank
    @Size(min = 7, max = 60)
    private String email;

    @Size(min = 9, max = 15)
    private String phone;
}
