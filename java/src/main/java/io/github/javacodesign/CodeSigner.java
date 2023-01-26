package io.github.javacodesign;

import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CodeSigner {

    private final static String JRE_PATH_IN_APP = "/Contents/PlugIns/jre";

    private final static List<String> DEFAULT_CODESIGNING_ARGS = List.of("-v", "--timestamp", "--force", "--options", "runtime");

    private final String identity;

    private final Path inputPath;

    private final Path launcher;

    private final Path entitlementsJvm;

    private final Path entitlementsLauncher;

    /**
     *
     * @param identity Developer certificate ID. For example, "Developer ID Application: John Doe (1234567890)"
     * @param inputPath Path to the .app to sign, for example MyApp.app
     * @param launcher Path to the executable that is launching the Java app
     * @param entitlementsJvm Path to the entitlements file that is used to code sign the Java binary
     * @param entitlementsLauncher Path to the entitlements file that is used to code sign the launcher
     */
    public CodeSigner(String identity, Path inputPath, Path launcher, Path entitlementsJvm, Path entitlementsLauncher) {
        this.identity = identity;
        this.inputPath = inputPath;
        this.launcher = launcher;
        this.entitlementsJvm = entitlementsJvm;
        this.entitlementsLauncher = entitlementsLauncher;
    }

    /**
     * Does the code signing.
     *
     * @throws IOException if an IO error occurs
     */
    public void sign() throws IOException {
        verifyInput();
        codeSignAll();
        codeSignJreExecutables();
        codeSignLaunchers();
    }

    private void codeSignLaunchers() {
        List<String> command = buildSignLaunchersCommand();
        CommandRunner commandRunner = new CommandRunner();
        commandRunner.runCommand(command);
    }

    /**
     * Removes all signatures, "de-signs" all files.
     */
    public void removeSignature() {
        List<String> command = buildRemoveSignatureCommand();
        CommandRunner commandRunner = new CommandRunner();
        commandRunner.runCommand(command);
    }

    private void codeSignAll() {
        List<String> command = buildCodeSignAllCommand();
        CommandRunner commandRunner = new CommandRunner();
        commandRunner.runCommand(command);
    }

    private void codeSignJreExecutables() {
        List<String> command = buildCodeSignJreExecutablesCommand();
        CommandRunner commandRunner = new CommandRunner();
        commandRunner.runCommand(command);
    }

    private List<String> buildSignLaunchersCommand(){
        List<String> result = new ArrayList<>();

        result.add("codesign");
        result.add("-s");
        result.add(identity);
        result.addAll(DEFAULT_CODESIGNING_ARGS);
        result.add("--entitlements");
        result.add(entitlementsLauncher.toString());
        result.add(launcher.toString());

        return result;
    }

    private List<String> buildRemoveSignatureCommand() {
        List<String> result = new ArrayList<>();

        result.add("find");
        result.add(inputPath.toString()+JRE_PATH_IN_APP);
        result.add("-type");
        result.add("f");
        result.add("-exec");
        result.add("codesign");
        result.add("--remove-signature");
        result.add("{}");
        result.add(";");

        return result;
    }

    private List<String> buildCodeSignJreExecutablesCommand() {
        List<String> result = new ArrayList<>();

        result.add("codesign");
        result.add("-s");
        result.add(identity);
        result.addAll(DEFAULT_CODESIGNING_ARGS);
        result.add("--entitlements");
        result.add(entitlementsJvm.toString());
        result.add(inputPath +JRE_PATH_IN_APP+"/Contents/Home/bin/java");
        result.add(inputPath +JRE_PATH_IN_APP+"/Contents/Home/bin/jrunscript");
        result.add(inputPath +JRE_PATH_IN_APP+"/Contents/Home/bin/keytool");

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

    private void verifyInput() throws IOException {
        checkExists(inputPath);
        checkExists(launcher);
        checkExists(entitlementsJvm);
        checkExists(entitlementsLauncher);
        checkCanRead(inputPath);
        checkCanExecute(launcher);
        checkCanRead(entitlementsJvm);
        checkCanRead(entitlementsLauncher);
    }

    private void checkExists(Path path) throws FileNotFoundException {
        if(!Files.exists(path)){
            throw new FileNotFoundException(path.toString());
        }
    }

    private void checkCanRead(Path path) throws IOException {
        if(!Files.isReadable(path)){
            throw new IOException("Cannot read " + path);
        }
    }

    private void checkCanExecute(Path path) throws IOException {
        if(!Files.isExecutable(path)){
            throw new IOException("Cannot execute " + path);
        }
    }


}
