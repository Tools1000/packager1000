package io.github.javacodesign;


import com.github.tools1000.CommandRunner;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class JPackager extends JavaCommandRunner {

    private String name;

    private String appVersion;

    private String module;

    private String input;

    private List<String> modulePath;

    private String runtimeImage;

    private String dest;

    private String icon;

    private String resourceDir;

    public JPackager() {

    }

    public boolean apply() throws IOException {
        verifyInput(this.name, this.module, this.dest, this.runtimeImage);
        return runCommand(buildJPackagerCommand());
    }

    @Override
    boolean wasSuccessful(CommandRunner.OutputStreams outputStreams) {
        boolean result = super.wasSuccessful(outputStreams);
        if(result){
            return !outputStreams.getSout().contains("Exception");
        }
        return false;
    }

    protected List<String> buildJPackagerCommand() {
        List<String> command = new ArrayList<>();

        command.add(getJavaHome()+"/bin/"+"jpackage");
        command.add("--runtime-image");
        command.add(runtimeImage);

        if(input != null && !input.isEmpty()){
            command.add("--input");
            command.add(input);
        }

        if(icon != null && !icon.isEmpty()){
            command.add("--icon");
            command.add(icon);
        }

        if(resourceDir != null && !resourceDir.isEmpty()){
            command.add("--resource-dir");
            command.add(resourceDir);
        }

        if(modulePath != null)
           modulePath.forEach(mp -> {
                command.add("--module-path");
                command.add(mp);

        });

        command.add("--module");
        command.add(module);
        command.add("--dest");
        command.add(dest);
        command.add("--name");
        command.add(name);
        command.add("--app-version");
        command.add(appVersion);
        command.add("--verbose");

        return command;
    }

    // Getter / Setter //


    public String getName() {
        return name;
    }

    public JPackager setName(String name) {
        this.name = name;
        return this;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public JPackager setAppVersion(String appVersion) {
        this.appVersion = appVersion;
        return this;
    }

    public String getModule() {
        return module;
    }

    public JPackager setModule(String module) {
        this.module = module;
        return this;
    }

    public String getInput() {
        return input;
    }

    public JPackager setInput(String input) {
        this.input = input;
        return this;
    }

    public List<String> getModulePath() {
        return modulePath;
    }

    public JPackager setModulePath(List<String> modulePath) {
        this.modulePath = modulePath;
        return this;
    }

    public String getRuntimeImage() {
        return runtimeImage;
    }

    public JPackager setRuntimeImage(String runtimeImage) {
        this.runtimeImage = runtimeImage;
        return this;
    }

    public String getDest() {
        return dest;
    }

    public JPackager setDest(String dest) {
        this.dest = dest;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public JPackager setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public String getResourceDir() {
        return resourceDir;
    }

    public JPackager setResourceDir(String resourceDir) {
        this.resourceDir = resourceDir;
        return this;
    }
}

