package com.bindord.jaipro.resourceserver.domain.bank.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
public class BankDto {

    private Integer id;

    @NotBlank
    @Size(min = 2, max = 200)
    private String name;

    @Size(min = 1, max = 10)
    private String shortName;

    private boolean enabled;

    public BankDto() {
    }

}
