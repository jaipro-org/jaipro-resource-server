package com.bindord.jaipro.resourceserver.domain.specialist.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class SpecialistExperienceUpdateDto {

    @NotNull
    @Valid
    private int index;

    @NotNull
    @Valid
    private Integer professionId;

    @Schema(description = "Experience time in a profession expressed in months")
    @Valid
    private Integer time;
}