package ru.kinoposisk.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Movie {
    private Rating rating;
    @JsonProperty("movieLength")
    private int length;
    private int id;
    private String type;
    private String name;
    private String description;
    private int year;
    private Poster poster;
    private List<Genre> genres;
    private List<Country> countries;
    private List<Person> persons;
    @JsonProperty("enName")
    private String englishName;
    private Logo logo;

}
