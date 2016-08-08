package com.example.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by Dmitrij on 30.07.2016.
 */
@Service
public class TestUtils {

    private ObjectMapper objectMapper;

    @Autowired
    public TestUtils(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private <T> T fromJson(byte[] input, Class<T> tClass) {
        T object = null;
        try {
            object = objectMapper.readValue(input, tClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return object;
    }

    public <T> byte[] convertToJson(T object) {
        byte[] objectInJson = new byte[0];
        try {
            objectInJson = objectMapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return objectInJson;
    }
}
