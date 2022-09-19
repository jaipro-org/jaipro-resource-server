package com.bindord.eureka.resourceserver.domain.specialist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;


@Setter
@Getter
@Table
public class WorkLocation implements Persistable<UUID> {

    @Id
    @Column
    private UUID specialistId;

    @NotNull
    @Column
    @Min(1)
    private Integer districtId;

    @NotBlank
    @Column
    private String countryId;

    @JsonIgnore
    @Transient
    private boolean isNew;

    @Override
    public boolean isNew() {
        return this.isNew;
    }

    @Override
    public UUID getId() {
        return this.specialistId;
    }
}
