package ru.kinoposisk.dto.quiz;

import lombok.Builder;
import lombok.Data;
import ru.kinoposisk.model.enums.CountryEnums;
import ru.kinoposisk.model.enums.GenreEnums;
import ru.kinoposisk.utils.EnumValue;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class QuizDTO {

    @NotEmpty(message = "Genre is required")
    @EnumValue(enumClass = GenreEnums.class, message = "Invalid genre")
    private GenreEnums genre;

    @NotEmpty(message = "Duration is required")
    @Pattern(regexp = "\\d+-\\d+", message = "The format should be 'number-number' for example '60-120'")
    private String duration;

    @NotEmpty(message = "Country is required")
    @EnumValue(enumClass = CountryEnums.class, message = "Invalid country")
    private CountryEnums country;
}
