package io.github.javacodesign;


import com.github.tools1000.CommandRunner;
import com.github.tools1000.Zipper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Notarizer extends InputVerifier {


    private final String primaryBundleId;

    private final String apiKey;

    private final String apiIssuer;

    private final Path inputPath;

    public Notarizer(String primaryBundleId, String apiKey, String apiIssuer, Path inputPath) {
        this.primaryBundleId = primaryBundleId;
        this.apiKey = apiKey;
        this.apiIssuer = apiIssuer;
        this.inputPath = inputPath;
    }

    /**
     * @return The request UUID
     * @throws IOException if an IO error occurs
     */
    public NotarizationRequestOutputParser.NotarizationOutputParserResult submitNotarizationRequest() throws IOException {
        verifyInput(inputPath);
        Path zipPath = new Zipper().zipFolder(inputPath);
        log.debug("Input zipped to {}", zipPath);
        CommandRunner.OutputStreams output = new CommandRunner().runCommand(buildNotarizeRequestCommand(zipPath));
        NotarizationRequestOutputParser.NotarizationOutputParserResult result = new NotarizationRequestOutputParser(output.getSout(), output.getSerr()).parse();
        return result;
    }

    public NotarizationResultOutputParser.NotarizationOutputParserResult.Status pollForNotarizationResult(String requestUuid) throws IOException {
        CommandRunner.OutputStreams output = new CommandRunner().runCommand(buildNotarizeResultCommand(requestUuid));
        NotarizationResultOutputParser.NotarizationOutputParserResult result = new NotarizationResultOutputParser(output.getSout(), output.getSerr()).parse();
        log.debug("Got notarization poll result: {}", result);
        return result.status;
    }

    public boolean notarize(int retries, long timeout) throws IOException {
        NotarizationRequestOutputParser.NotarizationOutputParserResult result = submitNotarizationRequest();
        log.debug("Got notarization result: {}", result);
        if(result.isOk()) {
            NotarizationResultOutputParser.NotarizationOutputParserResult.Status status = pollForNotarizationResult(result.getRequestUuid());
            int cnt = 1;
            while(NotarizationResultOutputParser.NotarizationOutputParserResult.Status.IN_PROGRESS == status && cnt <= retries){
                try {
                    Thread.sleep(timeout);
                } catch (InterruptedException e) {
                    throw new IOException(e);
                }
                log.debug("Checking for result again, attempt: {}", cnt);
                status = pollForNotarizationResult(result.getRequestUuid());
                cnt++;
            }
            return NotarizationResultOutputParser.NotarizationOutputParserResult.Status.SUCCESS == status;
        }
        return false;
    }

    private List<String> buildNotarizeResultCommand(String requestUuid) {
        List<String> result = new ArrayList<>();

        result.add("xcrun");
        result.add("altool");
        result.add("--notarization-info");
        result.add(requestUuid);
        result.add("--primary-bundle-id");
        result.add(primaryBundleId);
        result.add("--apiKey");
        result.add(apiKey);
        result.add("--apiIssuer");
        result.add(apiIssuer);

        return result;
    }

    private List<String> buildNotarizeRequestCommand(Path zipPath){
        List<String> result = new ArrayList<>();

        result.add("xcrun");
        result.add("altool");
        result.add("--notarize-app");
        result.add("--primary-bundle-id");
        result.add(primaryBundleId);
        result.add("--apiKey");
        result.add(apiKey);
        result.add("--apiIssuer");
        result.add(apiIssuer);
        result.add("-t");
        result.add("osx");
        result.add("-f");
        result.add(zipPath.toString());

        return result;
    }
}
