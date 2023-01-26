package io.github.javacodesign;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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

    public void notarize() throws IOException {
        verifyInput(inputPath);
        Path zipPath = new Zipper().zipFolder(inputPath);
        log.debug("Input zipped to {}", zipPath);
        CommandRunner.OutputStreams output = new CommandRunner().runCommand(buildNotarizeCommand(zipPath));
        String uuid = output.sout.substring(output.sout.indexOf("RequestUUID = ") + "RequestUUID = ".length()).trim();
        log.info("RequestUUID: {}", uuid);
    }

    private List<String> buildNotarizeCommand(Path zipPath){
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
