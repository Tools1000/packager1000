package io.github.javacodesign;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class NotarizationUploadOutputParser {

    private final String output;

    private final String serr;

    public NotarizerResponse parse() throws JsonProcessingException {


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        NotarizerResponse result = objectMapper.readValue(output, NotarizerResponse.class);

       return result;
    }
}
