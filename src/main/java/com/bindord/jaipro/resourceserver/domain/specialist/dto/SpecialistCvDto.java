package com.bindord.jaipro.resourceserver.domain.specialist.dto;

import com.bindord.jaipro.resourceserver.domain.json.Photo;
import com.bindord.jaipro.resourceserver.domain.specialist.json.Experience;
import com.bindord.jaipro.resourceserver.domain.specialist.json.SocialNetwork;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class SpecialistCvDto {

    private UUID id;

    @Size(min = 30, max = 1300)
    private String about;

    @Valid
    private List<SocialNetwork> socialNetworks;

    @Valid
    private List<Photo> gallery;

    @Valid
    private Photo profilePhoto;

    @NotEmpty
    @Valid
    private List<Experience> experienceTimes;

}
