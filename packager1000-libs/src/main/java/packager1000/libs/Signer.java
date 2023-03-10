package packager1000.libs;


import com.github.tools1000.CommandRunner;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class Signer extends InputVerifier {

    private final static List<String> DEFAULT_CODESIGNING_ARGS = Arrays.asList("-v", "--timestamp", "--force", "--options", "runtime");

    private final String identity;

    private final Path inputPath;

    private final Path launcher;

    private final Path entitlements;

    /**
     *
     * @param identity Developer certificate ID. For example, "Developer ID Application: John Doe (1234567890)"
     * @param inputPath Path to the .app to sign, for example MyApp.app
     * @param launcher Path to the executable that is launching the Java app
     */
    public Signer(String identity, Path inputPath, Path launcher, Path entitlements ) {
        this.identity = identity;
        this.inputPath = inputPath;
        this.launcher = launcher;
        this.entitlements = entitlements;
        verifyInput(identity);
    }

    public Signer(String identity, Path inputPath, Path launcher) {
        this(identity, inputPath, launcher, getPath("/default-entitlements.plist"));
    }

    private static Path getPath(String name) {
        try (InputStream in = Signer.class.getResourceAsStream(name);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            File tempDir = new File(System.getProperty("java.io.tmpdir"));
            File tempFile = new File(tempDir, name);
            tempFile.deleteOnExit();
            FileWriter fileWriter = new FileWriter(tempFile, true);
            BufferedWriter bw = new BufferedWriter(fileWriter);
            reader.lines().forEach(s -> {
                try {
                    bw.write(s);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            bw.close();
            log.info("Wrote {} to {}", name, tempFile);
            return tempFile.toPath();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Does the code signing.
     *
     * @throws IOException if an IO error occurs
     */
    public void sign() throws IOException {
        verifyInput();
        codeSignAll();
        codeSignJre();
        codeSignLaunchers();
    }

    private void verifyInput() throws IOException {
        verifyInput(inputPath, launcher, entitlements);
        checkCanExecute(launcher);
    }

    private void run(List<String> command) throws IOException {
        CommandRunner commandRunner = new CommandRunner();
        CommandRunner.OutputStreams result = commandRunner.runCommand(command);
        if(result.getSerr() != null && !result.getSerr().isEmpty()){
            log.warn("Command {} finished with errors: {}", command, result.getSerr());
        }
    }

    private void codeSignLaunchers() throws IOException {
        run(buildSignLaunchersCommand());
    }

    private void codeSignJre() throws IOException {
        run(buildCodeSignJreCommand());
    }

    /**
     * Removes all signatures, "de-signs" all files.
     */
    public void removeSignature() throws IOException {
        run(buildRemoveSignatureCommand());
    }

    private void codeSignAll() throws IOException {
        run(buildCodeSignAllCommand());
    }

    private List<String> buildSignLaunchersCommand(){
        List<String> result = new ArrayList<>();

        result.add("codesign");
        result.add("-s");
        result.add(identity);
        result.addAll(DEFAULT_CODESIGNING_ARGS);
        result.add("--entitlements");
        result.add(entitlements.toString());
        result.add(launcher.toString());

        return result;
    }

    private List<String> buildRemoveSignatureCommand() {
        List<String> result = new ArrayList<>();

        result.add("find");
        result.add(inputPath.toString()+ AppConfig.JRE_PATH_IN_APP);
        result.add("-type");
        result.add("f");
        result.add("-exec");
        result.add("codesign");
        result.add("--remove-signature");
        result.add("{}");
        result.add(";");

        return result;
    }

    private List<String> buildCodeSignAllCommand() {
        List<String> result = new ArrayList<>();

        result.add("find");
        result.add(inputPath.toString());
        result.add("-depth");
        result.add("-type");
        result.add("f");
        result.add("-exec");
        result.add("codesign");
        result.add("-s");
        result.add(identity);
        result.addAll(DEFAULT_CODESIGNING_ARGS);
        result.add("{}");
        result.add(";");

        return result;
    }

    private List<String> buildCodeSignJreCommand() {
        List<String> result = new ArrayList<>();

        result.add("find");
        result.add(inputPath.toString());
        result.add("-depth");
        result.add("-name");
        result.add("jspawnhelper");
        result.add("-exec");
        result.add("codesign");
        result.add("-s");
        result.add(identity);
        result.addAll(DEFAULT_CODESIGNING_ARGS);
        result.add("--entitlements");
        result.add(entitlements.toString());
        result.add("{}");
        result.add(";");

        return result;
    }



}
