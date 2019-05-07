package com.vr.commonutils.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil2 {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object fromJson(String jsonString, Class classType) {
        try {
            return objectMapper.readValue(jsonString, classType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object fromJson(String jsonString, TypeReference typeReference) {
        try {
            return objectMapper.readValue(jsonString, typeReference);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}


