package io.github.javacodesign;


import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Builder
@Slf4j
public class JPackager extends JavaCommandRunner {

    private final String name;

    private final String macPackageIdentifier;

    private final String appVersion;

    private final String module;

    private final String input;

    private final List<String> modulePath;

    private final String runtimeImage;

    private final String dest;

    private final String signingKeyUserName;

    private final String entitlements;

    /**
     * @param javaHome             JAVA_HOME
     * @param name                 Name of the application and/or package
     * @param macPackageIdentifier An identifier that uniquely identifies the application for macOS
     * @param appVersion           Version of the application and/or package
     * @param module               The main module (and optionally main class) of the application
     * @param input                Path of the input directory that contains the files to be packaged
     * @param modulePath           Each path is either a directory of modules or the path to a modular jar
     * @param runtimeImage         Path of the predefined runtime image that will be copied into the application image
     * @param dest                 Path where generated output file is placed
     * @param signingKeyUserName   Team or user name portion of Apple signing identities
     * @param entitlements         Path to file containing entitlements to use when signing executables and libraries in the bundle
     */
    public JPackager(String javaHome, String name, String macPackageIdentifier, String appVersion, String module, String input, List<String> modulePath, String runtimeImage, String dest, String signingKeyUserName, String entitlements) {
        super(javaHome);
        this.name = name;
        this.appVersion = appVersion;
        this.module = module;
        this.runtimeImage = runtimeImage;
        this.dest = dest;
        // may be null
        this.macPackageIdentifier = macPackageIdentifier;
        this.input = input;
        this.modulePath = modulePath;
        this.signingKeyUserName = signingKeyUserName;
        this.entitlements = entitlements;
    }

    /**
     * @param name                 Name of the application and/or package
     * @param macPackageIdentifier An identifier that uniquely identifies the application for macOS
     * @param appVersion           Version of the application and/or package
     * @param module               The main module (and optionally main class) of the application
     * @param input                Path of the input directory that contains the files to be packaged
     * @param modulePath           Each path is either a directory of modules or the path to a modular jar
     * @param runtimeImage         Path of the predefined runtime image that will be copied into the application image
     * @param dest                 Path where generated output file is placed
     * @param signingKeyUserName   Team or user name portion of Apple signing identities
     * @param entitlements         Path to file containing entitlements to use when signing executables and libraries in the bundle
     */
    public JPackager(String name, String macPackageIdentifier, String appVersion, String module, String input, List<String> modulePath, String runtimeImage, String dest, String signingKeyUserName, String entitlements) {
        this(JavaCommandRunner.javaHome(), name, macPackageIdentifier, appVersion, module, input, modulePath, runtimeImage, dest, signingKeyUserName, entitlements);
    }

    public boolean apply() throws IOException {
        verifyInput(this.name, this.module, this.dest, runtimeImage);
        return runCommand(buildJPackagerCommand());
    }

    private List<String> buildJPackagerCommand() {
        List<String> command = new ArrayList<>();

        command.add(getJavaHome()+"/bin/"+"jpackage");
        command.add("--type");
        command.add("app-image");
        command.add("--runtime-image");
        command.add(runtimeImage);

        if(input != null && !input.isEmpty()){
            command.add("--input");
            command.add(input);
        }

        if(signingKeyUserName != null && !signingKeyUserName.isEmpty()){
            command.add("--mac-sign");
            command.add("--mac-signing-key-user-name");
            command.add(signingKeyUserName);
        }
        if(entitlements != null && !entitlements.isEmpty()){
            command.add("--mac-entitlements");
            command.add(entitlements);
        }

        if(modulePath != null)
           modulePath.forEach(mp -> {
                command.add("--module-path");
                command.add(mp);

        });

        command.add("--module");
        command.add(module);

//        command.add("--main-jar");
//        command.add("/Users/alex/sources/drkodi/target/drkodi-mac-aarch64.jar");
//
//        command.add("--main-class");
//        command.add("drkodi.Launcher");


        command.add("--dest");
        command.add(dest);
        command.add("--name");
        command.add(name);
        command.add("--app-version");
        command.add(appVersion);
        if(macPackageIdentifier != null && !macPackageIdentifier.isEmpty()) {
            command.add("--mac-package-identifier");
            command.add(macPackageIdentifier);
        }
        command.add("--verbose");

        return command;
    }
}

