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
class Person {
    private int id;
    private String photo;
    private String name;
    @JsonProperty("enName")
    private String englishName;
    private String description;
    private String profession;
    @JsonProperty("enProfession")
    private String englishProfession;
}