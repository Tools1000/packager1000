package io.github.javacodesign;

import com.fasterxml.jackson.core.JsonProcessingException;


public class NotarizationInfoOutputParser extends JsonResponseParser {

    public NotarizationInfoOutputParser(String output) {
        super(output);
    }

    public NotarizerResponse parse() throws JsonProcessingException {
        return getObjectMapper().readValue(getOutput(), NotarizerResponse.class);
    }


}
