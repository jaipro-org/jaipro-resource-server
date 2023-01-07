package com.bindord.jaipro.resourceserver.validator;

import java.util.concurrent.CompletableFuture;

public interface Validator {

    CompletableFuture<Void> validateUUIDFormat(String uuid);
}
