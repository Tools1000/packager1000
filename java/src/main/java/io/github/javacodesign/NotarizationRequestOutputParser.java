package io.github.javacodesign;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class NotarizationRequestOutputParser {

    @Getter
    @ToString
    @AllArgsConstructor
    public static class NotarizationOutputParserResult {

        static final String UNKNOWN_ERROR = "unknown error";

        String requestUuid;

        String error;

        public boolean isOk() {
            return requestUuid != null && requestUuid.length() > 0;
        }
    }

    private final String output;

    private final String serr;

    public NotarizationOutputParserResult parse(){
        if(output != null && output.contains("RequestUUID")) {
            String requestUuid = output.substring(output.indexOf("RequestUUID = ") + "RequestUUID = ".length()).trim();
            return new NotarizationOutputParserResult(requestUuid, null);
        }
        else if(serr != null && serr.contains("Failed")){
            return new NotarizationOutputParserResult(null, serr);
        }
        return new NotarizationOutputParserResult(null, NotarizationOutputParserResult.UNKNOWN_ERROR);
    }
}
