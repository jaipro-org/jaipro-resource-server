package com.bindord.jaipro.resourceserver.domain.specialist.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.UUID;

@Setter
@Getter
public class SpecialistCvPresentationUpdateDto {

    private UUID id;

    @Size(min = 30, max = 1300)
    private String about;

    private String filePhoto;

    private String filePhotoExtension;

    private boolean flagUpdatePhoto;

}
