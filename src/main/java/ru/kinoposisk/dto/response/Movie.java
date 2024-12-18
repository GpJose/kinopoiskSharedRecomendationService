package ru.kinoposisk.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Movie {

    private Rating rating;
    @JsonProperty("movieLength")
    private int length;
    private Long id;
    private String type;
    private String name;
    private String description;
    private Integer year;
    @JsonProperty("poster")
    private Poster poster;
    private List<Genre> genres;
    private List<Country> countries;
    private List<Person> persons;
    @JsonProperty("enName")
    private String englishName;
    private Logo logo;

    public double getKpRating() {
        return this.rating.getKpRating();
    }
    public double getImdbRating() {
     return this.rating.getImdbRating();
    }
}
