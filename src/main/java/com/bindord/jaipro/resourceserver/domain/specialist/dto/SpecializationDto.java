package com.bindord.jaipro.resourceserver.domain.specialist.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Setter
@Getter
public class SpecializationDto {

    private Integer id;

    @Size(min = 2, max = 70)
    private String name;

    private Integer professionId;
}
