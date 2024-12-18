package ru.kinoposisk.utils.validators;

import org.springframework.beans.factory.annotation.Autowired;
import ru.kinoposisk.model.GenreReference;
import ru.kinoposisk.repository.GenreReferenceRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class GenreNameValidator  implements ConstraintValidator<ValidGenreName, String> {

    @Autowired
    private GenreReferenceRepository genreReferenceRepository;

    @Override
    public boolean isValid(String genreName, ConstraintValidatorContext context) {
        Optional<GenreReference> genre = genreReferenceRepository.searchByGenreName(genreName);
        return genre.isPresent();
    }
}