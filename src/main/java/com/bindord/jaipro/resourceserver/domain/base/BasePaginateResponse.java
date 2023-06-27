package com.bindord.jaipro.resourceserver.domain.base;

import lombok.Data;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.io.Serializable;
import java.util.List;

@Data
public class BasePaginateResponse<T> implements Serializable {

    private int totalRows;

    private List<T> data;

    public BasePaginateResponse(int totalRows, List<T> data){
        this.totalRows = totalRows;
        this.data = data;
    }
}