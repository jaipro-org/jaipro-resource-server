package com.bindord.jaipro.resourceserver.domain.specialist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotNull;
import java.util.UUID;


@Setter
@Getter
@Table
public class SpecialistSpecialization implements Persistable<UUID> {

    @Id
    @Column()
    private UUID specialistId;

    @NotNull
    @Column
    private Integer specializationId;

    @NotNull
    @Column
    private Integer professionId;

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
