package com.bindord.jaipro.resourceserver.domain.specialist.dto;

import com.bindord.jaipro.resourceserver.domain.json.Photo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.codec.multipart.FilePart;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class SpecialistGalleryUpdateDto {

    @Valid
    private UUID specialistCvId;

    @Valid
    private List<String> filesRemove;

    private List<FilePart> files;
}
