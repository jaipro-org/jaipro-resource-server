package com.bindord.jaipro.resourceserver.domain.profession;

import com.bindord.jaipro.resourceserver.domain.base.BaseDomain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.Size;

@Setter
@Getter
@Table
public class Profession extends BaseDomain implements Persistable<Integer> {

    @Id
    @Column(value = "profession_id")
    private Integer id;

    @Size(min = 2, max = 50)
    @Column
    private String name;

    public Profession() {
    }

    @JsonIgnore
    @Transient
    private boolean isNew;

    @Override
    public boolean isNew() {
        return this.isNew;
    }

}
