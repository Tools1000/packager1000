package io.github.javacodesign;

import com.github.ktools1000.CommandRunner;
import com.github.ktools1000.Zipper;
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
     *
     * @return The request UUID
     * @throws IOException if an IO error occurs
     */
    public String submitNotarizationRequest() throws IOException {
        verifyInput(inputPath);
        Path zipPath = new Zipper().zipFolder(inputPath);
        log.debug("Input zipped to {}", zipPath);
        CommandRunner.OutputStreams output = new CommandRunner().runCommand(buildNotarizeRequestCommand(zipPath));
        var result = new NotarizationRequestOutputParser(output.getSout()).parse();
        return result.requestUuid;
    }

    public boolean pollForNotarizationResult(String requestUuid) throws IOException {
        CommandRunner.OutputStreams output = new CommandRunner().runCommand(buildNotarizeResultCommand(requestUuid));
        var result = new NotarizationResultOutputParser(output.getSout()).parse();
        return "success".matches(result.status);
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
