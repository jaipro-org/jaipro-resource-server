package com.bindord.jaipro.resourceserver.domain.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class ServiceRequestListDto {

    private UUID id;

    private String professionName;

    private String detail;

    private LocalDateTime creationDate;

    private Integer proposalsCounter;

    private Integer rating;

    private boolean enabledRating;

    private boolean ratingDone;
}