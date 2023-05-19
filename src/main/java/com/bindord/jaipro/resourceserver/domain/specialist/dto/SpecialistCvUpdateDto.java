package com.bindord.jaipro.resourceserver.domain.specialist.dto;

import com.bindord.jaipro.resourceserver.domain.json.Photo;
import com.bindord.jaipro.resourceserver.domain.specialist.json.Experience;
import com.bindord.jaipro.resourceserver.domain.specialist.json.SocialNetwork;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class SpecialistCvUpdateDto {

    @Size(min = 30, max = 1300)
    private String about;

    private String filePhoto;

    private String filePhotoExtension;

    private boolean flagUpdatePhoto;
}