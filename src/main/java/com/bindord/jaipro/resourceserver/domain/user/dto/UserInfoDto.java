package com.bindord.jaipro.resourceserver.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Setter
@Getter
public class UserInfoDto {

    private UUID id;

    @NotNull
    @Min(value = 0)
    @Max(value = 99)
    private Integer profileType;

    @NotBlank
    @Size(min = 2, max = 36)
    private String profileName;

}
