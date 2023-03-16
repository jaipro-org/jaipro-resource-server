package com.bindord.jaipro.resourceserver.domain.specialist.dto;

import lombok.Getter;
import lombok.Setter;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class SpecialistSpecializationUpdateDto {

    @NotNull
    @Valid
    private UUID specialistId;

    @NotNull
    @Valid
    private Integer professionId;

    @NotNull
    @NotEmpty
    @Valid
    private List<Integer> listSpecialitiesIds;

}
