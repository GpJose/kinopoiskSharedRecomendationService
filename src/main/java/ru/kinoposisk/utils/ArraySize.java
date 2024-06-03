package ru.kinoposisk.utils;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ArraySizeValidator.class)
@Documented
public @interface ArraySize {
    String message() default "Invalid array size";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int size() default 0;
}
