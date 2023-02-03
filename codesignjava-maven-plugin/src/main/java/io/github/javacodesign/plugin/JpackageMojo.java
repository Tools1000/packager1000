package io.github.javacodesign.plugin;

import io.github.javacodesign.JLinker;
import io.github.javacodesign.JPackager;
import io.github.javacodesign.Notarizer;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Mojo(name = "package-and-codesign", defaultPhase = LifecyclePhase.PACKAGE)
public class JpackageMojo extends AbstractMojo {

    private final static String jlinkOut = "runtime";

    private final static String jpackageOut = "appdir";

    private final static String modsDir = "mods";

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;

    @Parameter(defaultValue = "${project.build.directory}", required = true, readonly = true)
    private File buildDirectory;

    @Parameter(required = true)
    String jreModuleNames;

    @Parameter(required = true)
    String jreModules;

    @Parameter
    String applicationModulesPath;

    @Parameter(required = true)
    String moduleName;

    @Parameter(required = true)
    String appVersion;

    @Parameter(required = true)
    String packageIdentifier;

    @Parameter(required = true)
    String moduleStarter;

    @Parameter
    String developerId;

    @Parameter
    String apiKey;

    @Parameter
    String apiIssuer;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        getLog().debug("project build directory: " + buildDirectory);
        getLog().debug("project artifact file: " + project.getArtifact().getFile().toString());
        getLog().debug("project artifacts: " + project.getArtifacts().toString());

        List<String> javaModules = Stream.of(this.jreModuleNames.split(",")).filter(Objects::nonNull).map(String::trim).collect(Collectors.toList());

        getLog().info("Config: jreModules: " + jreModules);
        getLog().info("Config: applicationModulesPath: " + applicationModulesPath);

        try {

        Path to = Paths.get(applicationModulesPath, project.getArtifact().getFile().getName().toString());
        Path from = project.getArtifact().getFile().toPath();
        getLog().info("Copying from " + from + " to " + to );
        Files.copy(from, to);

            if(new JLinker(javaModules, jreModules, buildDirectory + "/" + jlinkOut).apply()) {
                getLog().info("Jlink successful");

                JPackager jPackager = JPackager.builder()
                        .module(moduleStarter)
                        .name(moduleName)
                        .appVersion(appVersion)
                        .dest(relativeToBuildDirectory(jpackageOut))
                        .macPackageIdentifier(packageIdentifier)
                        .runtimeImage(relativeToBuildDirectory(jlinkOut))
                        .signingKeyUserName(developerId)
                        .modulePath(Collections.singletonList(applicationModulesPath))
                        .build();

                if(jPackager.apply()){
                    getLog().info("JPackage successful");

                    if(
                        packageIdentifier != null && !packageIdentifier.isEmpty()
                        && apiKey != null && !apiKey.isEmpty()
                        && apiIssuer != null && !apiIssuer.isEmpty()
                    ) {
                        getLog().info("Running notarization");
                        Notarizer notarizer = new Notarizer(packageIdentifier, apiKey, apiIssuer,Paths.get(relativeToBuildDirectory(jpackageOut)));
                        if(notarizer.notarize()){
                            getLog().info("Notarization successful");
                        } else {
                            getLog().info("Notarization did not succeed in time.");
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new MojoExecutionException(e.toString());
        }
    }

    private String relativeToBuildDirectory(String string) {
        return this.buildDirectory + "/" + string.trim();
    }
}
