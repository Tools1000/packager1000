package io.github.javacodesign;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class NotarizationRequestOutputParser {

    @ToString
    @AllArgsConstructor
    public static class NotarizationOutputParserResult {
        String requestUuid;
    }

    private final String output;

    public NotarizationOutputParserResult parse(){
        String requestUuid = output.substring(output.indexOf("RequestUUID = ") + "RequestUUID = ".length()).trim();
        return new NotarizationOutputParserResult(requestUuid);
    }
}
