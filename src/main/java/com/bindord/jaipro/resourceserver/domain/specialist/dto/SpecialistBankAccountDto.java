package com.bindord.jaipro.resourceserver.domain.specialist.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.UUID;

@Setter
@Getter
public class SpecialistBankAccountDto {

    private UUID id;

    @Valid
    @Size(min = 12, max = 24)
    private String accountNumber;

    @Valid
    @Size(min = 12, max = 30)
    private String cci;

    @Valid
    @Min(1)
    @Max(13)
    private Integer currency;

    @Valid
    private boolean preferred;

    @Valid
    private Integer specialistId;

    @Valid
    private Integer bankId;

}
