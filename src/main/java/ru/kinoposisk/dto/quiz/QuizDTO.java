package ru.kinoposisk.dto.quiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kinoposisk.model.enums.CountryEnums;
import ru.kinoposisk.model.enums.GenreEnums;
import ru.kinoposisk.utils.ArraySize;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizDTO {

    @NotEmpty(message = "Genre is required")
    @ArraySize(message = "Genre array size must be 3", size = 3)
    private GenreEnums[] genre;

    @NotEmpty(message = "Duration is required")
    @Pattern(regexp = "\\d+-\\d+", message = "The format should be 'number-number' for example '60-120'")
    private String duration;

    @NotEmpty(message = "Country is required")
    @ArraySize(message = "Country array size must be 3", size = 3)
    private CountryEnums[] country;
}

