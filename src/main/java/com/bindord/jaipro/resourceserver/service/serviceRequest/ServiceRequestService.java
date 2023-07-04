package com.bindord.jaipro.resourceserver.service.serviceRequest;

import com.bindord.jaipro.resourceserver.domain.base.BasePaginateResponse;
import com.bindord.jaipro.resourceserver.domain.base.BaseSearch;
import com.bindord.jaipro.resourceserver.domain.service.ServiceRequest;
import com.bindord.jaipro.resourceserver.domain.service.dto.ServiceRequestCreateDto;
import com.bindord.jaipro.resourceserver.domain.service.dto.ServiceRequestListDto;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Repository;
import com.bindord.jaipro.resourceserver.generic.BaseService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Repository
public interface ServiceRequestService extends BaseService<ServiceRequest, UUID, ServiceRequest, ServiceRequest> {

    Mono<Void> create(ServiceRequestCreateDto serviceRequestDto, List<FilePart> images);

    Mono<BasePaginateResponse<ServiceRequestListDto>> list(UUID customerId, BaseSearch searchDto);
}