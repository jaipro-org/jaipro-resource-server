package com.bindord.jaipro.resourceserver.domain.base;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Setter
@Getter
public class BaseDomain {

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime creationDate;

    @LastModifiedBy
    private String modifiedBy;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

}
