package ru.kinoposisk.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kinoposisk.model.CountryReference;
import ru.kinoposisk.utils.validators.ValidCountryName;

import java.io.Serializable;

/**
 * A DTO for the {@link CountryReference} entity
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class CountryReferenceDto implements Serializable {

    @JsonProperty("name")
    @ValidCountryName
    private String countryName;
    @JsonProperty("slug")
    private String slugName;
}