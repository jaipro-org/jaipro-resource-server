package com.bindord.jaipro.resourceserver.advice;

import static com.bindord.jaipro.resourceserver.utils.Constants.RESOURCE_NOT_FOUND;

public class NotFoundValidationException extends Exception {

    private String internalCode;

    public NotFoundValidationException(String internalCode) {
        super(RESOURCE_NOT_FOUND);
        this.internalCode = internalCode;
    }

    public String getInternalCode() {
        return internalCode;
    }
}
