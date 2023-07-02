package com.bindord.jaipro.resourceserver.domain.specialist.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SpecialistCvExperienceDto {
    String profession;

    Integer time;

    String specialties;
}