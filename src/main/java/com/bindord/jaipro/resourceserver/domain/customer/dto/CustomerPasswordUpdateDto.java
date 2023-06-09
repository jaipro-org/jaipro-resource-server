package com.bindord.jaipro.resourceserver.domain.customer.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Setter
@Getter
public class CustomerPasswordUpdateDto {

    @NotNull
    @Valid
    private UUID id;

    @NotBlank
    @Valid
    private String password;

    @NotBlank
    @Valid
    private String newPassword;
}
