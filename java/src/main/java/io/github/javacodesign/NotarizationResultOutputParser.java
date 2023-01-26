package io.github.javacodesign;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class NotarizationResultOutputParser {

    @ToString
    @AllArgsConstructor
    public static class NotarizationOutputParserResult {
        String status;

        String statusMessage;
    }

    private final String output;

    public NotarizationResultOutputParser.NotarizationOutputParserResult parse(){
        String status = output.substring(output.indexOf("Status: ") + "Status: ".length()).split("\\s" )[0].trim();
        return new NotarizationResultOutputParser.NotarizationOutputParserResult(status, null);
    }
}
