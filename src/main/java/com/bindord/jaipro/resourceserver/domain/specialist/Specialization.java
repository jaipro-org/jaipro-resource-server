package com.bindord.jaipro.resourceserver.domain.specialist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.Size;

@Table
@Setter
@Getter
public class Specialization implements Persistable<Integer> {

    @Id
    @Column(value = "specialization_id")
    private Integer id;

    @Size(min = 2, max = 70)
    private String name;

    @Column
    private Integer professionId;

    @JsonIgnore
    @Transient
    private boolean isNew;

    public Specialization() {
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }
}
