package com.example.CuoiKy.validator.annotation;

import com.example.CuoiKy.validator.ValidBookIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidBookIdValidator.class)
public @interface ValidBookId {
    String message() default "Invalid Book ID";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
