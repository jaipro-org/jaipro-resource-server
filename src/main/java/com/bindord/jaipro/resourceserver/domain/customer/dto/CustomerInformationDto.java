package com.bindord.jaipro.resourceserver.domain.customer.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class CustomerInformationDto {
    private UUID id;

    private String name;
    private String lastName;
    private String email;
    private String phone;
    private Integer districtId;
    private String address;
    private String avatar;
}
