package com.bindord.jaipro.resourceserver.domain.specialist.dto;

import com.bindord.jaipro.resourceserver.domain.base.BaseSearch;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SpecialistFiltersSearchDto extends BaseSearch {

    private List<Integer> categories;

    private List<Integer> specialties;

    private List<Integer> districts;
}
