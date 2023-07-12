package com.bindord.jaipro.resourceserver.service.serviceRequest.impl;

import com.bindord.jaipro.resourceserver.advice.CustomValidationException;
import com.bindord.jaipro.resourceserver.advice.NotFoundValidationException;
import com.bindord.jaipro.resourceserver.domain.base.BasePaginateResponse;
import com.bindord.jaipro.resourceserver.domain.base.BaseSearch;
import com.bindord.jaipro.resourceserver.domain.json.Photo;
import com.bindord.jaipro.resourceserver.domain.service.ServiceRequest;
import com.bindord.jaipro.resourceserver.domain.service.dto.ServiceRequestCreateDto;
import com.bindord.jaipro.resourceserver.domain.service.dto.ServiceRequestListDto;
import com.bindord.jaipro.resourceserver.repository.ServiceRequestRepository;
import com.bindord.jaipro.resourceserver.service.gcloud.GoogleCloudService;
import com.bindord.jaipro.resourceserver.service.serviceRequest.ServiceRequestService;
import com.bindord.jaipro.resourceserver.utils.Constants;
import com.bindord.jaipro.resourceserver.utils.Enums;
import io.r2dbc.postgresql.codec.Json;
import lombok.AllArgsConstructor;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import static com.bindord.jaipro.resourceserver.utils.Enums.Status.OPEN;

import java.util.List;
import java.util.UUID;

import static com.bindord.jaipro.resourceserver.utils.Utilitarios.serializeObject;
import static java.time.LocalDateTime.now;

@AllArgsConstructor
@Service
public class ServiceRequestServiceImpl implements ServiceRequestService {

    private final ServiceRequestRepository repository;

    private final GoogleCloudService googleCloudService;

    @Override
    public Mono<ServiceRequest> save(ServiceRequest entity) throws NotFoundValidationException, CustomValidationException {
        return null;
    }

    @Override
    public Mono<ServiceRequest> update(ServiceRequest entity) throws NotFoundValidationException, CustomValidationException {
        return null;
    }

    @Override
    public Mono<ServiceRequest> findOne(UUID id) throws NotFoundValidationException {
        return null;
    }

    @Override
    public Mono<Void> delete(UUID id) {
        return null;
    }

    @Override
    public Mono<Void> delete() {
        return null;
    }

    @Override
    public Flux<ServiceRequest> findAll() {
        return null;
    }

    @Override
    public Mono<Void> create(ServiceRequestCreateDto serviceRequestDto, List<FilePart> images) {

        if(images.isEmpty())
            return Mono.error(new CustomValidationException("Debe ingresar como minimo un archivo"));

        if(images.size() > Constants.MAX_SERVICE_REQUEST_GALLERY_FILES)
            return Mono.error(new CustomValidationException("Ha excedido el numero maximo de archivos permitidos para subir a la galeria. Maximo 5."));

        return repository
                .save(convertToEntityForNewCase(serviceRequestDto))
                .flatMap(qSr -> {
                    return Flux.fromIterable(images)
                        .flatMap(newPhoto ->
                            getBytesToFilePart(newPhoto)
                             .flatMap(photoBytes -> googleCloudService.saveServiceRequestGallery(photoBytes, newPhoto.filename(), serviceRequestDto.getCustomerId(), qSr.getId()))
                             .flatMap(urlOutput -> {
                                 Photo photo = new Photo();
                                 photo.setDate(now());
                                 photo.setName(newPhoto.filename());
                                 photo.setSize(0);
                                 photo.setUrl(urlOutput);
                                 return Mono.just(photo);
                             })
                        )
                        .collectList()
                        .flatMap(photos -> {
                            qSr.setNew(false);
                            return saveServiceRequestGallery(photos, qSr);
                        });
                })
                .then(Mono.empty());
    }

    @Override
    public Mono<BasePaginateResponse<ServiceRequestListDto>> list(UUID customerId, BaseSearch searchDto) {

        var data = repository.getListServiceRequestByCustomerId(customerId, searchDto.getPageNumber(), searchDto.getPageSize());
        var totalRows = repository.getTotalRowsInListServiceRequestByCustomerId(customerId);

        return Mono.zip(data.collectList(), totalRows)
                .flatMap(tuple ->
                        Mono.just(new BasePaginateResponse<ServiceRequestListDto>(tuple.getT2(), tuple.getT1()))
                );
    }

    private Mono<Void> saveServiceRequestGallery(List<Photo> gallery, ServiceRequest qEntity) {
        qEntity.setGallery(Json.of(serializeObject(gallery)));
        return repository.save(qEntity).then(Mono.empty());
    }

    private ServiceRequest convertToEntityForNewCase(ServiceRequestCreateDto dto){
        var serviceRequest = new ServiceRequest();
        serviceRequest.setCustomerId(dto.getCustomerId());
        serviceRequest.setProfessionId(dto.getProfessionId());
        serviceRequest.setType(0);
        serviceRequest.setStatus(OPEN.get());
        serviceRequest.setDetail(dto.getDetail());
        serviceRequest.setProposalsCounter(0);
        serviceRequest.setCondition(0);
        serviceRequest.setServiceTypeId(1);
        serviceRequest.setDistrictId(dto.getDistrictId());
        serviceRequest.setNew(true);

        return serviceRequest;
    }

    private Mono<byte[]> getBytesToFilePart(FilePart file) {
        return DataBufferUtils.join(file.content())
                .map(dataBuffer -> dataBuffer.asByteBuffer().array())
                .map(x -> x);
    }
}