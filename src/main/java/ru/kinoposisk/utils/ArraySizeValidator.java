package ru.kinoposisk.utils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ArraySizeValidator implements ConstraintValidator<ArraySize, Object> {

    private int size;

    @Override
    public void initialize(ArraySize constraintAnnotation) {
        this.size = constraintAnnotation.size();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        if (!value.getClass().isArray()) {
            return true;
        }
        Object[] array = (Object[]) value;
        return array.length == size;
    }
}
