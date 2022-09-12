package com.bindord.eureka.resourceserver.domain.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResponse {

    private String message;
    private String code;

    private ErrorResponse(){}

    public ErrorResponse(String message) {
        this.message = message;
    }

    public ErrorResponse(String message, String code) {
        this.message = message;
        this.code = code;
    }

}
