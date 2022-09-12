package com.bindord.eureka.resourceserver.domain.customer;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Setter
@Getter
@Table
public class WorkLocation {

    @Column
    private UUID specialistId;

    @Column
    private Integer districtId;

    @Column
    private String countryId;

}
