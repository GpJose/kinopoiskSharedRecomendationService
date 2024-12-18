package ru.kinoposisk.dto.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rating {
    @JsonProperty("kp")
    private double kpRating;
    @JsonProperty("imdb")
    private double imdbRating;
    @JsonProperty("filmCritics")
    private double filmCriticsRating;
    @JsonProperty("russianFilmCritics")
    private double russianFilmCriticsRating;
}
