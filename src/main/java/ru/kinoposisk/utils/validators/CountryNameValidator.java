package ru.kinoposisk.utils.validators;

import org.springframework.beans.factory.annotation.Autowired;
import ru.kinoposisk.repository.CountryReferenceRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CountryNameValidator implements ConstraintValidator<ValidCountryName, String> {

    @Autowired
    private CountryReferenceRepository countryReferenceRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return countryReferenceRepository.findByCountryName(value).isPresent();
    }
}
