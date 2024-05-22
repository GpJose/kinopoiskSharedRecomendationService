package ru.kinoposisk.utils;

import ru.kinoposisk.model.enums.GenreEnums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.stream.Collectors;

@Converter
public class GenreEnumConverter implements AttributeConverter<GenreEnums[], String> {

    private static final String DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(GenreEnums[] attribute) {
        if (attribute == null || attribute.length == 0) {
            return null;
        }
        return Arrays.stream(attribute)
                .map(GenreEnums::name)
                .collect(Collectors.joining(DELIMITER));
    }

    @Override
    public GenreEnums[] convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        String[] values = dbData.split(DELIMITER);
        return Arrays.stream(values)
                .map(GenreEnums::valueOf)
                .toArray(GenreEnums[]::new);
    }
}