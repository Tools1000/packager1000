package io.github.javacodesign;

import com.fasterxml.jackson.core.JsonProcessingException;

public class NotarizationUploadOutputParser extends JsonResponseParser {

    public NotarizationUploadOutputParser(String output) {
        super(output);
    }

    public NotarizerResponse parse() throws JsonProcessingException {
        return getObjectMapper().readValue(getOutput(), NotarizerResponse.class);
    }
}
