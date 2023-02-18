package io.github.javacodesign.plugin;

import io.github.javacodesign.*;
import net.lingala.zip4j.ZipFile;
import org.apache.commons.lang3.SystemUtils;
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
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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

    @Parameter(required = true)
    String moduleName;

    @Parameter(required = true)
    String appVersion;

    @Parameter(required = true)
    String moduleStarter;

    @Parameter
    String packageIdentifier;

    @Parameter
    String applicationModulesPath;

    @Parameter
    String macDeveloperId;

    @Parameter
    String macKeychainProfile;

    @Parameter
    String winUpgradeUuid;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        getLog().debug("project build directory: " + buildDirectory);
        getLog().debug("project artifact file: " + project.getArtifact().getFile().toString());
        getLog().debug("project artifacts: " + project.getArtifacts().toString());

        List<String> javaModules = Stream.of(this.jreModuleNames.split(",")).filter(Objects::nonNull).map(String::trim).collect(Collectors.toList());

        getLog().info("Config: jreModules: " + jreModules);
        getLog().info("Config: applicationModulesPath: " + applicationModulesPath);

        try {

            Path to = Paths.get(applicationModulesPath, project.getArtifact().getFile().getName());
            Path from = project.getArtifact().getFile().toPath();
            getLog().info("Copying from " + from + " to " + to);
            Files.copy(from, to);

            if (!new JLinker(javaModules, jreModules, buildDirectory + "/" + jlinkOut).apply()) {
                throw new MojoFailureException("Jlink failed.");
            }

            getLog().info("Jlink successful");

            if (SystemUtils.IS_OS_WINDOWS) {
                runWindows();
            }
            if (SystemUtils.IS_OS_LINUX) {
                runLinux();
            }
            if (SystemUtils.IS_OS_MAC) {
                runMac();
            }

        } catch (IOException e) {
            throw new MojoExecutionException(e.toString());
        }

    }

    private void runWindows() throws IOException, MojoFailureException {
        JPackager jPackager = new WindowsJPackager()
                .setModule(moduleStarter)
                .setName(moduleName)
                .setAppVersion(appVersion)
                .setDest(relativeToBuildDirectory(jpackageOut))
                .setRuntimeImage(relativeToBuildDirectory(jlinkOut))
                .setModulePath(Collections.singletonList(applicationModulesPath))
                .setWinUpgradeUuid(winUpgradeUuid);

        if (!jPackager.apply()) {
            throw new MojoFailureException("JPackage failed.");
        }

        getLog().info("JPackage successful");

    }

    private void runLinux() throws MojoFailureException, IOException {
        runAppImage();
        Path path = Paths.get(relativeToBuildDirectory(jpackageOut), moduleName);
        try(ZipFile zipFile = new ZipFile(path + "-"+appVersion + ".zip")){
            zipFile.addFolder(path.toFile());
        }
        getLog().info("Zipped app image to " + path + "-"+appVersion + ".zip");

        if(new DebDetector().apply())
            runDmg();
        else if(new RpmDetector().apply())
            runRpm();
        else
            throw new MojoFailureException("Could neither find dmg nor rpm");

    }

    private void runAppImage() throws MojoFailureException, IOException {
        JPackager jPackager = new LinuxAppImageJPackager()
                .setModule(moduleStarter)
                .setName(moduleName)
                .setAppVersion(appVersion)
                .setDest(relativeToBuildDirectory(jpackageOut))
                .setRuntimeImage(relativeToBuildDirectory(jlinkOut))
                .setModulePath(Collections.singletonList(applicationModulesPath));

        if (!jPackager.apply()) {
            throw new MojoFailureException("JPackage (appImage) failed.");
        }

        getLog().info("JPackage (appImage) successful");
    }

    private void runRpm() throws MojoFailureException, IOException {
        JPackager jPackager = new LinuxRpmJPackager()
                .setModule(moduleStarter)
                .setName(moduleName)
                .setAppVersion(appVersion)
                .setDest(relativeToBuildDirectory(jpackageOut))
                .setRuntimeImage(relativeToBuildDirectory(jlinkOut))
                .setModulePath(Collections.singletonList(applicationModulesPath));

        if (!jPackager.apply()) {
            throw new MojoFailureException("JPackage (rpm) failed.");
        }

        getLog().info("JPackage (rpm) successful");
    }

    private void runDmg() throws MojoFailureException, IOException {
        JPackager jPackager = new LinuxDebJPackager()
                .setModule(moduleStarter)
                .setName(moduleName)
                .setAppVersion(appVersion)
                .setDest(relativeToBuildDirectory(jpackageOut))
                .setRuntimeImage(relativeToBuildDirectory(jlinkOut))
                .setModulePath(Collections.singletonList(applicationModulesPath));

        if (!jPackager.apply()) {
            throw new MojoFailureException("JPackage (deb) failed.");
        }

        getLog().info("JPackage (deb) successful");
    }

    private void runMac() throws MojoFailureException, IOException {
        JPackager jPackager = new MacJPackager()
                .setModule(moduleStarter)
                .setName(moduleName)
                .setAppVersion(appVersion)
                .setDest(relativeToBuildDirectory(jpackageOut))
                .setRuntimeImage(relativeToBuildDirectory(jlinkOut))
                .setModulePath(Collections.singletonList(applicationModulesPath))
                // mac specific
                .setMacPackageIdentifier(packageIdentifier)
                .setMacSigningKeyUserName(macDeveloperId);


        if (!jPackager.apply()) {
            throw new MojoFailureException("JPackage failed.");
        }

        getLog().info("JPackage successful");

        Path dmgPath = Paths.get(jPackager.getDest(), jPackager.getName() + "-" + jPackager.getAppVersion() + ".dmg");

        getLog().info("Code signing dmg");

        if (!new DmgCodeSigner().setDeveloperId(macDeveloperId).setDmgPath(dmgPath.toString()).apply()) {
            throw new MojoFailureException("Code signing dmg failed");
        }

        getLog().info("Code signing dmg successful");

        getLog().info("Running notarization");

        Notarizer notarizer = new Notarizer(Paths.get(jPackager.getDest(), jPackager.getName() + "-" + jPackager.getAppVersion() + ".dmg"), macKeychainProfile);

        if (!notarizer.notarize()) {
            throw new MojoFailureException("Notarization failed.");
        }
        getLog().info("Notarization successful");
        getLog().info("Running notarization stapler");
        if (!new NotarizationStapler().setDmgPath(dmgPath.toString()).apply()) {
            throw new MojoFailureException("Notarization stapler failed.");
        }
        getLog().info("Notarization stapler successful");
    }

    private String relativeToBuildDirectory(String string) {
        return Paths.get(this.buildDirectory.toString(), string.trim()).toString();
    }
}
