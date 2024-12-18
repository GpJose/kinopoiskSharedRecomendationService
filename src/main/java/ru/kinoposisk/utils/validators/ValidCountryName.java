package ru.kinoposisk.utils.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CountryNameValidator.class)
public @interface ValidCountryName {
    String message() default "Invalid country name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
