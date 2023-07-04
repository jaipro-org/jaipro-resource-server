package com.bindord.jaipro.resourceserver.controller;

import com.bindord.jaipro.resourceserver.domain.service.dto.ServiceRequestCreateDto;
import com.bindord.jaipro.resourceserver.service.serviceRequest.ServiceRequestService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("${service.ingress.context-path}/service-request")
public class ServiceRequestController {

    private final ServiceRequestService service;

    @ApiResponse(description = "create service request", responseCode = "200")
    @PostMapping(value = "",
                produces = {MediaType.APPLICATION_JSON_VALUE},
                consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Mono<Void> create(@RequestPart List<FilePart> images,
                             @RequestPart @Valid ServiceRequestCreateDto serviceRequest){
        return service.create(serviceRequest, images);
    }
}