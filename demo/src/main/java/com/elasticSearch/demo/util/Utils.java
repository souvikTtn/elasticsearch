package com.elasticSearch.demo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;

public class Utils {
    private static ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy/MM/dd"));
    }

    public static <T> T getObjectFromString(Class<T> t, String json)
    throws JsonProcessingException {
        return objectMapper.readValue(json, t);
    }

    public static <T> String getJsonFromObject(T t)
    throws JsonProcessingException {
        return objectMapper.writeValueAsString(t);
    }
}






