package io.github.javacodesign;


import com.github.tools1000.CommandRunner;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.ZipFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Notarizer extends InputVerifier {

    static final int DEFAULT_RETRIES = 5;

    /**
     * One minute default wait time
     */
    static final int DEFAULT_TIMEOUT = 1000 * 60;


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
        Path uploadPath;
        if (inputPath.toString().toLowerCase().endsWith(".zip")) {
            uploadPath = inputPath;
        } else {
            try (ZipFile zipFile = new ZipFile(inputPath + ".zip")) {
                zipFile.addFolder(inputPath.toFile());
                log.debug("Input zipped to {}", zipFile);
                uploadPath = Path.of(inputPath.toString(), ".zip");
            }
        }

        log.info("Uploading {} for notarization", uploadPath);
        NotarizationUploadOutputParser parser = new NotarizationUploadOutputParser(new CommandRunner().runCommand(buildNotarizeRequestCommand(uploadPath)).getSout());
        return parser.parse();
    }

        public NotarizerResponse getNotarizationInfo (String requestUuid) throws IOException {
            log.info("Querying for notarization result");
            NotarizerResponse result = new NotarizationInfoOutputParser(new CommandRunner().runCommand(buildNotarizeResultCommand(requestUuid)).getSout()).parse();
        log.debug("Got notarization poll result: {}", result);
        return result;
    }


    public boolean notarize() throws IOException {
        return notarize(DEFAULT_RETRIES, DEFAULT_TIMEOUT);
    }

    public boolean notarize(int retries, long timeout) throws IOException {
        NotarizerResponse notarizationUploadResult = notarizationUpload();
        log.info("Got notarization notarizationUploadResult: {}", notarizationUploadResult);
        if(notarizationUploadResult.isUploadOk()) {
            NotarizerResponse notarizationInfoResult = getNotarizationInfo(notarizationUploadResult.getNotarizationUpload().requestUuid);
            int cnt = 1;
            while("in progress".equals(notarizationInfoResult.notarizationInfo.status)&& cnt <= retries){
                try {
                    Thread.sleep(timeout);
                } catch (InterruptedException e) {
                    throw new IOException(e);
                }
                log.debug("Checking for notarizationUploadResult again, attempt: {}", cnt);
                notarizationInfoResult = getNotarizationInfo(notarizationUploadResult.getNotarizationUpload().requestUuid);
                cnt++;
            }
            log.info("NotarizationInfoResult: {}", notarizationInfoResult);
        }
        return notarizationUploadResult.isUploadOk();
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
