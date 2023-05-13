package com.bindord.jaipro.resourceserver.domain.specialist.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SpecialistSearchDto {

    private List<Integer> categories;

    private List<Integer> specialties;

    private List<Integer> districts;
}
