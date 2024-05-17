package ru.kinoposisk.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Mapper {

    private static ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    public static String objectToJson(Object object) throws JsonProcessingException {
        return getObjectMapper().writeValueAsString(object);
    }

    public static <T> T jsonToObject(String json, Class<T> clazz) throws JsonProcessingException {
        return getObjectMapper().readValue(json, clazz);
    }
}
