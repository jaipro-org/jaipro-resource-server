package com.bindord.jaipro.resourceserver.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
public class ApiSubError {

    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

}