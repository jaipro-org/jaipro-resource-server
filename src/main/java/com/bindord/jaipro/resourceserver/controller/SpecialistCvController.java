package com.bindord.jaipro.resourceserver.controller;

import com.bindord.jaipro.resourceserver.advice.CustomValidationException;
import com.bindord.jaipro.resourceserver.advice.NotFoundValidationException;
import com.bindord.jaipro.resourceserver.domain.specialist.SpecialistCv;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistCvDto;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistCvPresentationUpdateDto;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistCvUpdateDto;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistExperienceUpdateDto;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistGalleryUpdateDto;
import com.bindord.jaipro.resourceserver.domain.specialist.json.Experience;
import com.bindord.jaipro.resourceserver.service.gcloud.GoogleCloudService;
import com.bindord.jaipro.resourceserver.service.specialist.SpecialistCvService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("${service.ingress.context-path}/specialist-cv")
public class SpecialistCvController {

    private final SpecialistCvService specialistCvService;

    private final GoogleCloudService googleCloudService;

    @ApiResponse(description = "Persist a specialist cv",
            responseCode = "200")
    @PostMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<SpecialistCv> save(@Valid @RequestBody SpecialistCvDto specialist)
            throws NotFoundValidationException, CustomValidationException {
        return specialistCvService.save(specialist);
    }

    @ApiResponse(description = "Update a specialist cv",
            responseCode = "200")
    @PutMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<SpecialistCv> update(@PathVariable UUID id, @Valid @RequestBody SpecialistCvUpdateDto specialist)
            throws NotFoundValidationException, CustomValidationException {
        specialist.setId(id);
        return specialistCvService.update(specialist);
    }

    @ApiResponse(description = "Update a specialist cv but only the fields related to presentation card",
            responseCode = "200")
    @PutMapping(value = "/{id}/presentation",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<SpecialistCv> updatePresentation(@PathVariable UUID id, @Valid @RequestBody SpecialistCvPresentationUpdateDto specialist) {
        specialist.setId(id);
        return uploadFile(id, specialist)
                .flatMap(urlFile -> specialistCvService.updatePresentation(id, specialist, urlFile));
    }

    @ApiResponse(description = "List specialist's cvs",
            responseCode = "200")
    @GetMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Flux<SpecialistCv> findAll() {
        return specialistCvService.findAll();
    }

    @ApiResponse(description = "Find by id",
            responseCode = "200")
    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<SpecialistCv> findById(@PathVariable UUID id) throws NotFoundValidationException {
        return specialistCvService.findOne(id);
    }

    @ApiResponse(description = "Update an experience of specialist cv",
            responseCode = "200")
    @PutMapping(value = "/{id}/experience",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Void> updateExperience(@PathVariable UUID id, @Valid @RequestBody SpecialistExperienceUpdateDto specialistExperience) {
        return specialistCvService.updateExperience(id, specialistExperience);
    }

    @ApiResponse(description = "Persist an experience of specialist cv",
            responseCode = "200")
    @PostMapping(value = "/{id}/experience",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Experience> persistExperience(@PathVariable UUID id, @Valid @RequestBody Experience experience) {
        return specialistCvService.saveExperience(id, experience);
    }

    @ApiResponse(description = "Delete an experience of specialist cv", responseCode = "200")
    @DeleteMapping(value = "/{id}/experience/{professionId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Void> deleteExperienceByIdAndProfessionId(
            @PathVariable UUID id, @PathVariable Integer professionId) throws NotFoundValidationException {
        return specialistCvService.deleteExperienceByIdAndProfessionId(id, professionId);
    }

    @ApiResponse(description = "Update gallery to specialist cv",
            responseCode = "200")
    @PutMapping(value = "/gallery",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Mono<SpecialistCv> updateGallery(@RequestPart List<FilePart> images,
                                            @RequestPart @Valid SpecialistGalleryUpdateDto specialistGallery) {
        List<String> filesToRemove = specialistGallery.getFileIdsToRemove();
        specialistGallery.setFileIdsToRemove(filesToRemove == null || filesToRemove.isEmpty() ?
                Collections.emptyList() : filesToRemove);
        return specialistCvService.updateGallery(images, specialistGallery);
    }

    @ApiResponse(description = "Delete image from gallery of specialist cv",
            responseCode = "200")
    @DeleteMapping(value = "/gallery/{specialistId}/{imageName}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Void> deleteImageFromGallery(@PathVariable UUID specialistId, @PathVariable String imageName) {
        return specialistCvService.removeFromGallery(specialistId, imageName);
    }

    private Mono<String> uploadFile(UUID specialistId, SpecialistCvPresentationUpdateDto specialist) {
        try {
            if (!specialist.isFlagUpdatePhoto())
                return Mono.just("");

            if (specialist.isFlagUpdatePhoto() && specialist.getFilePhoto().isBlank()) {
                return googleCloudService
                        .removeSpecialistPhoto(specialistId.toString(), specialist.getFilePhotoExtension())
                        .then(Mono.just(""));
            }

            byte[] data = Base64.decodeBase64(specialist.getFilePhoto());

            return googleCloudService
                    .saveSpecialistPhoto(data, specialistId, specialist.getFilePhotoExtension());
        } catch (Exception ex) {
            return Mono.just(ex.getMessage());
        }
    }
}