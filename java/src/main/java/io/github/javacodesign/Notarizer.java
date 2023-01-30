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
    public NotarizerResponse notarizationUpload() throws IOException {
        verifyInput(inputPath);
        Path zipPath = new Zipper().zipFolder(inputPath);
        log.debug("Input zipped to {}", zipPath);
        CommandRunner.OutputStreams output = new CommandRunner().runCommand(buildNotarizeRequestCommand(zipPath));
       NotarizationUploadOutputParser parser = new NotarizationUploadOutputParser(output.getSout(), output.getSerr());
        return parser.parse();
    }

    public NotarizerResponse getNotarizationInfo(String requestUuid) throws IOException {
        CommandRunner.OutputStreams output = new CommandRunner().runCommand(buildNotarizeResultCommand(requestUuid));
        NotarizerResponse result = new NotarizationInfoOutputParser(output.getSout(), output.getSerr()).parse();
        log.debug("Got notarization poll result: {}", result);
        return result;
    }

    public boolean notarize(int retries, long timeout) throws IOException {
        NotarizerResponse result = notarizationUpload();
        log.debug("Got notarization result: {}", result);
        if(result.notarizationUpload.isOk()) {
            NotarizerResponse pollResult = getNotarizationInfo(result.getNotarizationUpload().requestUuid);
            int cnt = 1;
            while("in progress".equals(pollResult.notarizationInfo.status)&& cnt <= retries){
                try {
                    Thread.sleep(timeout);
                } catch (InterruptedException e) {
                    throw new IOException(e);
                }
                log.debug("Checking for result again, attempt: {}", cnt);
                pollResult = getNotarizationInfo(result.getNotarizationUpload().requestUuid);
                cnt++;
            }
        }
        return result.notarizationUpload.isOk();
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
        result.add("--output-format");
        result.add("json");

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
        result.add("--output-format");
        result.add("json");
        result.add("-f");
        result.add(zipPath.toString());

        return result;
    }
}
