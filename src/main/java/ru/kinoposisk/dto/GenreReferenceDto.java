package ru.kinoposisk.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kinoposisk.model.GenreReference;
import ru.kinoposisk.utils.validators.ValidGenreName;

import java.io.Serializable;

/**
 * A DTO for the {@link GenreReference} entity
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class GenreReferenceDto implements Serializable {

    @JsonProperty("name")
    @ValidGenreName
    private String genreName;
    @JsonProperty("slug")
    private String slugGenre;

}