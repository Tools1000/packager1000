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

        enum Status {
            SUCCESS, IN_PROGRESS, FAILED
        }

        Status status;

        String statusMessage;
    }

    private final String output;

    private final String serr;

    public NotarizationResultOutputParser.NotarizationOutputParserResult parse(){
        if(output != null && output.contains("Status: success"))
            return new NotarizationResultOutputParser.NotarizationOutputParserResult(NotarizationOutputParserResult.Status.SUCCESS, output);
        if(output != null && output.contains("Status: in progress")){
            return new NotarizationResultOutputParser.NotarizationOutputParserResult(NotarizationOutputParserResult.Status.IN_PROGRESS, output);
        }
        if(output != null && output.contains("Status: in progress")){
            return new NotarizationResultOutputParser.NotarizationOutputParserResult(NotarizationOutputParserResult.Status.IN_PROGRESS, output);
        }
        if(serr != null && !isDeprecationWarning(serr)){
            return new NotarizationResultOutputParser.NotarizationOutputParserResult(NotarizationOutputParserResult.Status.FAILED, serr);
        }
        log.warn("Failed to parse notarization result from sout: {}, serr: {}", output, serr);
        return new NotarizationResultOutputParser.NotarizationOutputParserResult(NotarizationOutputParserResult.Status.FAILED, "Parser error");

    }

    private boolean isDeprecationWarning(String serr) {
        return serr != null && serr.contains("Warning") && serr.contains("deprecated");
    }
}
