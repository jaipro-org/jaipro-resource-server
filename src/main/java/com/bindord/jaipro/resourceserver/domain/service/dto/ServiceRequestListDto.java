package com.bindord.jaipro.resourceserver.domain.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class ServiceRequestListDto {

    private UUID id;

    private String category;

    private String detail;

    private String creationDate;

    private Integer proposalsCounter;
}