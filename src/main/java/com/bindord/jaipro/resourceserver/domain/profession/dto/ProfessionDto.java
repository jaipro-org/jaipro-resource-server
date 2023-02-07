package com.bindord.jaipro.resourceserver.domain.profession.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import javax.validation.constraints.Size;

@Setter
@Getter
public class ProfessionDto {

    @Id
    @Column(value = "profession_id")
    private Integer id;

    @Size(min = 2, max = 50)
    @Column
    private String name;

    public ProfessionDto() {
    }

}
