package com.litevar.opentool.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class JsonUtil {

    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.setDefaultPropertyInclusion(
                JsonInclude.Value.construct(JsonInclude.Include.NON_NULL, JsonInclude.Include.ALWAYS)
        );
    }

    private JsonUtil() {
        // Utility class
    }

    /**
     * Convert object to JSON string
     *
     * @param object The object to convert
     * @return JSON string representation
     * @throws JsonProcessingException if conversion fails
     */
    public static String toJsonString(Object object) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(object);
    }

    /**
     * Convert JSON string to object of specified type
     *
     * @param jsonString The JSON string
     * @param valueType  The target type
     * @param <T>        The type parameter
     * @return The converted object
     * @throws JsonProcessingException if conversion fails
     */
    public static <T> T fromJsonString(String jsonString, Class<T> valueType) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(jsonString, valueType);
    }

    /**
     * Convert object to Map
     *
     * @param object The object to convert
     * @return Map representation
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(Object object) {
        return OBJECT_MAPPER.convertValue(object, Map.class);
    }

    /**
     * Convert Map to object of specified type
     *
     * @param map       The map to convert
     * @param valueType The target type
     * @param <T>       The type parameter
     * @return The converted object
     */
    public static <T> T fromMap(Map<String, Object> map, Class<T> valueType) {
        return OBJECT_MAPPER.convertValue(map, valueType);
    }

    /**
     * Get the shared ObjectMapper instance
     *
     * @return ObjectMapper instance
     */
    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }
}