package ru.kinoposisk.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kinoposisk.model.enums.CountryEnums;
import ru.kinoposisk.model.enums.GenreEnums;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieSearchRequestDTO {
    private int page;
    private int limit;
    private List<String> selectFields;
    private String sortField;
    private int sortType;
    private String type;
    @JsonProperty("rating.kp")
    private String ratingKP;
    @JsonProperty("movieLength")
    private String movieLength;
    @JsonProperty("genres.name")
    private List<GenreEnums> genres;
    @JsonProperty("countries.name")
    private List<CountryEnums> countries;
}
