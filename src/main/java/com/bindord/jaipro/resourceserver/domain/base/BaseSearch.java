package com.bindord.jaipro.resourceserver.domain.base;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Setter
@Getter
public class BaseSearch implements Serializable {

    @Size(max = 60)
    private String sortColumn;

    @Size(max = 4)
    private String sortDirection;

    @PositiveOrZero
    private Integer pageNumber;

    @Positive
    @Max(100)
    private Integer pageSize;

    private Boolean enabled;
}
