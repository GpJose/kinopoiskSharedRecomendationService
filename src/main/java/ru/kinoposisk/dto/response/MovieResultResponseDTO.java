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
public class MovieResultResponseDTO {
    @JsonProperty("docs")
    private List<Movie> movies;
    private int total;
    private int limit;
    private int page;
    private int pages;

}
