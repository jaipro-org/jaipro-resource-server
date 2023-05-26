package com.bindord.jaipro.resourceserver.domain.specialist.dto;

import io.r2dbc.postgresql.codec.Json;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
public class SpecialistResultSearchDTO {

    private UUID specialistId;

    private String fullName;

    private String photo;

    private String assessment;

    private String about;

    private String professions;
}
