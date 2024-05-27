package ru.kinoposisk.utils;

import ru.kinoposisk.model.enums.CountryEnums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.stream.Collectors;

@Converter
public class CountryEnumConverter implements AttributeConverter<CountryEnums[], String> {

    private static final String DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(CountryEnums[] attribute) {
        if (attribute == null || attribute.length == 0) {
            return null;
        }
        return Arrays.stream(attribute)
                .map(CountryEnums::name)
                .collect(Collectors.joining(DELIMITER));
    }

    @Override
    public CountryEnums[] convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        String[] values = dbData.split(DELIMITER);
        return Arrays.stream(values)
                .map(CountryEnums::valueOf)
                .toArray(CountryEnums[]::new);
    }
}