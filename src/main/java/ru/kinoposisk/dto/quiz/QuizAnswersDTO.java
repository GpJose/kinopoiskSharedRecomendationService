package ru.kinoposisk.dto.quiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kinoposisk.model.CountryReference;
import ru.kinoposisk.model.GenreReference;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizAnswersDTO {

    @NotEmpty(message = "Genre is required")
    @Size(min = 3, max = 3, message = "Genre size must be 3")
    private GenreReference[] genre;

    @NotEmpty(message = "Duration is required")
    @Pattern(regexp = "\\d+-\\d+", message = "The format should be 'number-number' for example '60-120'")
    private String duration;

    @NotEmpty(message = "Country is required")
    @Size(message = "Country size must be 3", min = 3, max = 3)
    private CountryReference[] country;
}

