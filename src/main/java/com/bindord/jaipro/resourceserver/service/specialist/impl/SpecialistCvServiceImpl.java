package com.bindord.jaipro.resourceserver.service.specialist.impl;

import com.bindord.jaipro.resourceserver.advice.CustomValidationException;
import com.bindord.jaipro.resourceserver.advice.NotFoundValidationException;
import com.bindord.jaipro.resourceserver.domain.json.Photo;
import com.bindord.jaipro.resourceserver.domain.specialist.SpecialistCv;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistCvDto;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistCvUpdateDto;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistExperienceUpdateDto;
import com.bindord.jaipro.resourceserver.domain.specialist.dto.SpecialistGalleryUpdateDto;
import com.bindord.jaipro.resourceserver.domain.specialist.json.Experience;
import com.bindord.jaipro.resourceserver.repository.SpecialistCvRepository;
import com.bindord.jaipro.resourceserver.service.gcloud.GoogleCloudService;
import com.bindord.jaipro.resourceserver.service.specialist.SpecialistCvService;
import com.bindord.jaipro.resourceserver.utils.Constants;
import com.fasterxml.jackson.core.type.TypeReference;
import io.r2dbc.postgresql.codec.Json;
import io.r2dbc.spi.Connection;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.bindord.jaipro.resourceserver.utils.Constants.ERROR_EXPERIENCE_REPEATED;
import static com.bindord.jaipro.resourceserver.utils.Utilitarios.convertJSONtoString;
import static com.bindord.jaipro.resourceserver.utils.Utilitarios.getNullPropertyNames;
import static com.bindord.jaipro.resourceserver.utils.Utilitarios.instanceObjectMapper;
import static com.bindord.jaipro.resourceserver.utils.Utilitarios.serializeObject;
import static java.time.LocalDateTime.now;
import static java.util.Objects.isNull;
import static org.springframework.util.CollectionUtils.isEmpty;

@AllArgsConstructor
@Service
public class SpecialistCvServiceImpl implements SpecialistCvService {

    private final SpecialistCvRepository repository;

    private final GoogleCloudService googleCloudService;

    @Override
    public Mono<SpecialistCv> save(SpecialistCvDto entity) throws NotFoundValidationException, CustomValidationException {
        return repository.save(convertToEntityForNewCase(entity));
    }

    @Override
    public Mono<SpecialistCv> update(SpecialistCvUpdateDto entity) throws NotFoundValidationException, CustomValidationException {
        Mono<SpecialistCv> qSpecialistCv = repository.findById(entity.getId());
        return qSpecialistCv.flatMap(qCus -> repository.save(convertToEntity(entity, qCus)));
    }

    @Override
    public Mono<SpecialistCv> findOne(UUID id) throws NotFoundValidationException {
        return repository.findById(id);
    }

    private <T> Mono<T> close(Connection connection) {
        return Mono.from(connection.close())
                .then(Mono.empty());
    }

    @Override
    public Mono<Void> delete(UUID id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono<Void> delete() {
        return repository.deleteAll();
    }

    @Override
    public Flux<SpecialistCv> findAll() {
        return repository.findAll();
    }

    @Override
    public Flux<SpecialistCv> findAllNative() {
        return repository.findAll();
    }

    @Override
    public Mono<Void> updateExperience(UUID id, SpecialistExperienceUpdateDto entity) {
        Mono<SpecialistCv> qSpecialistCv = repository.findById(id);
        return qSpecialistCv.flatMap(qScv -> {
            var experiences = convertJsonToClass(qScv.getExperienceTimes());
            var experience = experiences.get(entity.getIndex());
            experience.setTime(entity.getTime());
            experiences.set(entity.getIndex(), experience);

            qScv.setExperienceTimes(Json.of(convertJSONtoString(experiences)));
            return repository.save(qScv);
        }).then(Mono.empty());
    }

    @Override
    public Mono<Experience> saveExperience(UUID id, Experience experience) {
        Mono<SpecialistCv> qSpecialistCv = repository.findById(id);
        return qSpecialistCv.flatMap(qScv -> {
            var experiences = convertJsonToClass(qScv.getExperienceTimes());
            if (experiences.stream().anyMatch(exp -> exp.getProfessionId().equals(experience.getProfessionId()))) {
                return Mono.error(new CustomValidationException(ERROR_EXPERIENCE_REPEATED));
            }
            experience.setDate(now());
            experiences.add(experience);

            qScv.setExperienceTimes(Json.of(convertJSONtoString(experiences)));
            return repository.save(qScv).then(Mono.just(experience));
        });
    }

    @Override
    public Mono<SpecialistCv> updateGallery(List<FilePart> files, SpecialistGalleryUpdateDto entity) {

        Mono<SpecialistCv> qSpecialistCv = repository.findById(entity.getSpecialistId());
        return qSpecialistCv.flatMap(qScv -> {
            List<Photo> gallery = qScv.getGallery() == null
                    ? Collections.emptyList() : convertJsonToClassPhoto(qScv.getGallery());
            List<Photo> finalGallery = gallery.stream()
                    .filter(gall ->
                            !entity.getFileIdsToRemove().contains(gall.getUrl()))
                    .collect(Collectors.toList());

            if (files.size() + finalGallery.size() > Constants.MAX_GALLERY_FILES) {
                return Mono.error(new CustomValidationException(
                        "Ha excedido el numero maximo de archivos permitidos para subir a la galeria. Maximo 6."));
            }

            return Flux.fromIterable(files)
                    .flatMap(nwPhoto -> getBytesToFilePart(nwPhoto)
                            .flatMap(photoBytes -> googleCloudService.saveSpecialistGallery(photoBytes,
                                    entity.getSpecialistId(),
                                    nwPhoto.filename())
                            ).flatMap(urlOutput -> {
                                Photo photo = new Photo();
                                photo.setDate(now());
                                photo.setName(nwPhoto.filename());
                                photo.setSize(0);
                                photo.setUrl(urlOutput);
                                return Mono.just(photo);
                            }))
                    .collectList()
                    .flatMap(
                            photos -> {
                                finalGallery.addAll(photos);
                                return saveSpecialistGallery(finalGallery, qScv);
                            });
        });
    }

    private Mono<SpecialistCv> saveSpecialistGallery(List<Photo> gallery, SpecialistCv qSvc) {
        qSvc.setGallery(Json.of(serializeObject(gallery)));
        return repository.save(qSvc);
    }

    private Flux<Integer> sizeFile(FilePart file) {
        return file.content() // for one file, is a Flux with one element
                .map(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    return bytes.length;
                });
    }

    @SneakyThrows
    private List<Experience> convertJsonToClass(Json json) {
        var objectMapper = instanceObjectMapper();

        return objectMapper.readValue(json.asString(), new TypeReference<>() {
        });
    }

    @SneakyThrows
    private List<Photo> convertJsonToClassPhoto(Json json) {
        var objectMapper = instanceObjectMapper();

        return objectMapper.readValue(json.asString(), new TypeReference<>() {
        });
    }

    @SneakyThrows
    private SpecialistCv convertToEntity(SpecialistCvUpdateDto obj, SpecialistCv specialistCv) {
        BeanUtils.copyProperties(obj, specialistCv, getNullPropertyNames(obj));
        var objMapper = instanceObjectMapper();
        if (!isEmpty(obj.getSocialNetworks()))
            specialistCv.setSocialNetworks(
                    Json.of(
                            objMapper.writeValueAsString(obj.getSocialNetworks())
                    ));
        if (!isEmpty(obj.getGallery()))
            specialistCv.setGallery(
                    Json.of(
                            objMapper.writeValueAsString(obj.getGallery())
                    ));
        if (!isEmpty(obj.getExperienceTimes()))
            specialistCv.setExperienceTimes(
                    Json.of(
                            objMapper.writeValueAsString(obj.getExperienceTimes())
                    ));
        if (!isNull(obj.getProfilePhoto()))
            specialistCv.setProfilePhoto(
                    Json.of(
                            objMapper.writeValueAsString(obj.getProfilePhoto())
                    )
            );
        return specialistCv;
    }

    private SpecialistCv convertToEntityForNewCase(SpecialistCvDto obj) {
        obj.getExperienceTimes().forEach(exp -> exp.setDate(now()));

        var specialistCv = new SpecialistCv();
        BeanUtils.copyProperties(obj, specialistCv);
        serializeJsonColumns(specialistCv, obj);
        specialistCv.setNew(true);
        return specialistCv;
    }

    @SneakyThrows
    private void serializeJsonColumns(SpecialistCv specialistCv, SpecialistCvDto obj) {
        var objMapper = instanceObjectMapper();
        if (!isEmpty(obj.getSocialNetworks()))
            specialistCv.setSocialNetworks(
                    Json.of(
                            objMapper.writeValueAsString(obj.getSocialNetworks())
                    )
            );
        if (!isEmpty(obj.getGallery()))
            specialistCv.setGallery(
                    Json.of(
                            objMapper.writeValueAsString(obj.getGallery())
                    )
            );
        if (!isNull(obj.getProfilePhoto()))
            specialistCv.setProfilePhoto(
                    Json.of(
                            objMapper.writeValueAsString(obj.getProfilePhoto())
                    )
            );
        if (!isEmpty(obj.getExperienceTimes()))
            specialistCv.setExperienceTimes(
                    Json.of(
                            objMapper.writeValueAsString(obj.getExperienceTimes())
                    )
            );
    }

    private Mono<byte[]> getBytesToFilePart(FilePart file) {
        return DataBufferUtils.join(file.content())
                .map(dataBuffer -> dataBuffer.asByteBuffer().array())
                .map(x -> x);
    }
}
