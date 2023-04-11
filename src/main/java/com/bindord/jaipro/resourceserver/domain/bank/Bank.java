package com.bindord.jaipro.resourceserver.domain.bank;

import com.bindord.jaipro.resourceserver.domain.base.BaseDomain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Table
@Data
@EqualsAndHashCode(callSuper = false)
public class Bank extends BaseDomain implements Persistable<Integer> {

    @Id
    @Column(value = "bank_id")
    private Integer id;

    @NotBlank
    @Size(min = 2, max = 200)
    @Column
    private String name;


    @Size(min = 1, max = 10)
    @Column
    private String shortName;

    @Column
    private boolean enabled;

    @JsonIgnore
    @Transient
    private boolean isNew;

    public Bank() {
    }

    @Override
    public boolean isNew() {
        return this.isNew;
    }
}
