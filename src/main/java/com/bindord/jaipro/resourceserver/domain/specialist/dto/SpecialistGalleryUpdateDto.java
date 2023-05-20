package com.bindord.jaipro.resourceserver.domain.specialist.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.codec.multipart.FilePart;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class SpecialistGalleryUpdateDto {

    @NotNull
    private UUID specialistId;

    private List<String> fileIdsToRemove;
}