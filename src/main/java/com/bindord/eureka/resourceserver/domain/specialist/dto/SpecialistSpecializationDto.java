package com.bindord.eureka.resourceserver.domain.specialist.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;


@Setter
@Getter
public class SpecialistSpecializationDto {

    @NotNull
    private UUID specialistId;

    @NotNull
    private Integer specializationId;

    @NotNull
    private Integer professionId;

}
