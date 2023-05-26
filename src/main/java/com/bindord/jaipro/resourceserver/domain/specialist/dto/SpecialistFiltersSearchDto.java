package com.bindord.jaipro.resourceserver.domain.specialist.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SpecialistFiltersSearchDto {

    private List<Integer> categories;

    private List<Integer> specialties;

    private List<Integer> districts;

    private int page;

    private int pageSize;
}
