package com.nineleaps.productservice.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author Dilip Chauhan
 * Created on 30/03/2020
 */
@Slf4j
public final class JsonUtils {
    private JsonUtils() {
    }

    public static String serialize(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        String valueAsString = "";
        try {
            valueAsString = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Error while converting object to json string: " + e.getMessage(), e);
        }
        return valueAsString;
    }

    public static <T> T deserialize(String jsonString, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        T obj = null;
        try {
            obj = objectMapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            log.error("Error while converting json string to object: " + e.getMessage(), e);
        }
        return obj;
    }
}
