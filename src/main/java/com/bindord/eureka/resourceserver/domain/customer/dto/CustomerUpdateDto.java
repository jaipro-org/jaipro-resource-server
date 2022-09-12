package com.bindord.eureka.resourceserver.domain.customer.dto;

import com.bindord.eureka.resourceserver.domain.json.Photo;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.UUID;

@Setter
@Getter
public class CustomerUpdateDto {

    private UUID id;

    @Size(min = 2, max = 36)
    private String name;

    @Size(min = 2, max = 36)
    private String lastName;

    @Size(min = 2, max = Byte.MAX_VALUE)
    private String address;

    @Size(min = 9, max = 15)
    private String phone;

    @Valid
    private Photo profilePhoto;

    @Min(1)
    @Max(Short.MAX_VALUE)
    private Integer districtId;

}
