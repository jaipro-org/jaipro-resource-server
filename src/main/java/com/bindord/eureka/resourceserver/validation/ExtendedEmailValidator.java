package com.bindord.eureka.resourceserver.validation;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Schema(example = "pedropablo.182@gmail.com")
@Pattern(regexp=".+@.+\\..+", message="Please enter a valid email")
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface ExtendedEmailValidator {
    String message() default "Please enter a valid email";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}