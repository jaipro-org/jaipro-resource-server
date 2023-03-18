package com.bindord.jaipro.resourceserver.advice;

import com.bindord.jaipro.resourceserver.domain.exception.ApiError;
import com.bindord.jaipro.resourceserver.domain.exception.ApiSubError;
import com.bindord.jaipro.resourceserver.domain.exception.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.spi.R2dbcDataIntegrityViolationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.r2dbc.BadSqlGrammarException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import static com.bindord.jaipro.resourceserver.configuration.JacksonFactory.getObjectMapper;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    private static final ObjectMapper mapper = getObjectMapper();

    private static final String SQL_UNIQUE_VIOLATION_CODE = "23505";
    private static final String SQL_DUP_EXCEP_PREFIX = "duplicate key value violates unique constraint";
    private static final String SQL_DUP_EXCEP_PREFIX_ES = "llave duplicada viola restricción de unicidad";
    private static final String TEMP_UNIQUE_CONS_ONE = "uk8jmdau039u32ktqkckcdgnvkt";
    private static final String TEMP_UNIQUE_CONS_TWO = "uk_7rr530m3pxabetp6s9r0fjp37";
    private static final String TEMP_UC_ONE_MSG = "¡El código del producto ya ha sido escaneado con anterioridad, no puede haber códigos duplicados!";
    private static final String TEMP_UC_TWO_MSG = "El usuario que intenta registrar, ya ha sido registrado con anterioridad. Si no lo encuentra en su lista de contactos, es porque este ha sido registrado por otro vendedor.";

    private static final Logger LOGGER = LogManager.getLogger(ExceptionControllerAdvice.class);
    public static final String BINDING_ERROR = "Validation has failed";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ApiError> handleBindException(WebExchangeBindException ex) {
        LOGGER.warn("method {}", "handleBindException");
        ex.getModel().entrySet().forEach(e -> {
            LOGGER.warn(e.getKey() + ": " + e.getValue());
        });
        List<ApiSubError> errors = new ArrayList<>();

        for (FieldError x : ex.getBindingResult().getFieldErrors()) {
            errors.add(new ApiSubError(x.getObjectName(), x.getField(), x.getRejectedValue(), x.getDefaultMessage()));
        }
        return Mono.just(new ApiError(BINDING_ERROR, errors));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<ApiError> handleIllegalArgumentException(IllegalArgumentException ex) {
        LOGGER.warn("method {}", "handleIllegalArgumentException");
        return Mono.just(new ApiError(ex.getMessage(), ex));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundValidationException.class)
    public @ResponseBody
    Mono<ApiError> handlerNotFoundValidationException(NotFoundValidationException ex) {
        LOGGER.warn("method {}", "handlerNotFoundValidationException");
        return Mono.just(new ApiError(ex));
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(WebClientResponseException.class)
    public @ResponseBody
    Mono<ErrorResponse> handlerWebClientResponseException(WebClientResponseException ex)
            throws JsonProcessingException {
        LOGGER.warn("method {}", "handlerWebClientResponseException");
        LOGGER.warn(ex.getMessage());
        var lenStackTrace = ex.getStackTrace().length;
        for (int i = 0; i < lenStackTrace; i++) {
            LOGGER.warn(ex.getStackTrace()[i].toString());
        }
        if (ex.getRawStatusCode() == HttpStatus.UNAUTHORIZED.value()) {
            var err = new ErrorResponse();
            err.setCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
            err.setMessage(ex.getMessage());
            return Mono.just(err);
        }
        return Mono.just(mapper.readValue(ex.getResponseBodyAsString(), ErrorResponse.class));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public @ResponseBody
    Mono<ErrorResponse> handlerDataIntegrityViolationException(DataIntegrityViolationException ex) {
        LOGGER.warn("method {}", "handlerDataIntegrityViolationException");

        R2dbcDataIntegrityViolationException cve = null;
        boolean isCVE = ex.getCause() instanceof R2dbcDataIntegrityViolationException;
        if (isCVE) {
            cve = (R2dbcDataIntegrityViolationException) ex.getCause();
        }

        String mostSpecCause = ex.getMostSpecificCause().toString();
        LOGGER.warn(mostSpecCause);
        for (int i = 0; i < ex.getStackTrace().length; i++) {
            LOGGER.warn(ex.getStackTrace()[i].toString());
        }

        if (mostSpecCause.contains(SQL_DUP_EXCEP_PREFIX) ||
                mostSpecCause.contains(SQL_DUP_EXCEP_PREFIX_ES)) {
            if (mostSpecCause.contains(TEMP_UNIQUE_CONS_ONE)) {
                return Mono.just(new ErrorResponse(TEMP_UC_ONE_MSG, SQL_UNIQUE_VIOLATION_CODE));
            }

            if (mostSpecCause.contains(TEMP_UNIQUE_CONS_TWO)) {
                return Mono.just(new ErrorResponse(TEMP_UC_TWO_MSG, SQL_UNIQUE_VIOLATION_CODE));
            }
        }
        String sqlStateCode = isCVE ? cve.getSqlState() : "";
        if (sqlStateCode.isEmpty()) {
            return Mono.just(new ErrorResponse(mostSpecCause, SQL_UNIQUE_VIOLATION_CODE));
        } else {
            var prevCause = cve.getCause();
            String cause = prevCause != null ? cve.getCause().getMessage() : cve.getMessage();
            return Mono.just(new ErrorResponse(cause, sqlStateCode));
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadSqlGrammarException.class)
    public @ResponseBody
    Mono<ApiError> handlerBadSqlGrammarException(BadSqlGrammarException ex) {
        LOGGER.warn("method {}", "handlerBadSqlGrammarException");
        return Mono.just(new ApiError(ex.getMessage(), ex));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Mono<ApiError> handleConstraintViolationException(ConstraintViolationException ex) {
        LOGGER.warn("method {}", "handleConstraintViolationException");
        LOGGER.warn(ex.getMessage());
        return Mono.just(new ApiError(ex.getMessage(), ex));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ServerWebInputException.class)
    public @ResponseBody
    Mono<ApiError> handleServerWebInputException(ServerWebInputException ex) {
        LOGGER.warn("method {}", "handleServerWebInputException");
        return Mono.just(new ApiError(ex.getMessage(), ex));
    }
}


