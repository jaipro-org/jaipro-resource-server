package com.bindord.jaipro.resourceserver.domain.service;

import com.bindord.jaipro.resourceserver.domain.base.BaseDomain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.r2dbc.postgresql.codec.Json;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Setter
@Getter
@Table
public class ServiceRequest extends BaseDomain implements Persistable<UUID> {

    @Id
    @Column(value = "service_request_id")
    private UUID id;

    @Column
    private UUID customerId;

    @Column
    private Integer professionId;

    @Column
    private UUID specialistId;

    @Column
    private Integer type;

    @Column
    private Integer status;

    @Column
    private String detail;

    @Column
    private Integer proposalsCounter;

    @Column
    private boolean enabledRating;

    @Column
    private boolean ratingDone;

    @Column
    private Integer condition;

    @Column
    private Json milestones;

    @Column
    private Json gallery;

    @Column
    private Integer serviceTypeId;

    @Column
    private Integer districtId;

    @JsonIgnore
    @Transient
    private boolean isNew;

    @Override
    public boolean isNew() {
        return this.isNew;
    }
}