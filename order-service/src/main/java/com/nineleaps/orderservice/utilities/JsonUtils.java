package com.nineleaps.orderservice.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * A utility class for serialize to json and de-serialize to supplied class.
 *
 * @author Dilip Chauhan
 * Created on 22/03/2020
 */
@Slf4j
public final class JsonUtils {

    private JsonUtils() {
    }

    /**
     * Serialize object.
     *
     * @param object the object
     * @return the string
     */
    public static String serialize(final Object object) {
        final ObjectMapper objectMapper = new ObjectMapper();
        String valueAsString = "";
        try {
            valueAsString = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Error while converting object to json string: " + e.getMessage(), e);
        }
        return valueAsString;
    }

    /**
     * Deserialize t type.
     *
     * @param <T>        the type parameter
     * @param jsonString the json string
     * @param clazz      the class
     * @return the t
     */
    public static <T> T deserialize(final String jsonString, final Class<T> clazz) {
        final ObjectMapper objectMapper = new ObjectMapper();
        T obj = null;
        try {
            obj = objectMapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            log.error("Error while converting json string to object: " + e.getMessage(), e);
        }
        return obj;
    }
}
