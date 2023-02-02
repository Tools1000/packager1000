package io.github.javacodesign;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JsonResponseParser {

    private final String output;

    private final ObjectMapper objectMapper;

    public JsonResponseParser(String output) {
        this.output = output;
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    protected String getOutput() {
        return output;
    }

    protected ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
