package com.cometrica.javajuniortask.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=PageableValidator.class)
public @interface PageableConstraint {

    String message() default "page parameters constraints have violated";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    int minSize() default 20;
    int maxSize() default 20;

    SortPropertyConstraint[] orders() default {};

    boolean allowUnpaged() default false;
}