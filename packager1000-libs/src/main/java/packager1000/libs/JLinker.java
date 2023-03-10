package packager1000.libs;

import com.github.tools1000.CommandRunner;
import lombok.Builder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Builder
public class JLinker extends JavaCommandRunner {

    private final static String defaultJlinkOutputArgument = "jre";

    private final static String defaultJlinkModulePathArgument = "jmods";

    private final static List<String> defaultJlinkArguments = Arrays.asList("--strip-native-commands", "--no-header-files", "--no-man-pages", "--strip-debug", "--compress=1");

    private final List<String> modulesToAdd;

    private final String modulePath;

    private final String output;

    public JLinker(String javaHome, List<String> modulesToAdd, String modulePathToAdd, String output) {
        super(javaHome);
        this.modulePath = modulePathToAdd;
        this.output = output;
        this.modulesToAdd = modulesToAdd;
    }

    public JLinker(List<String> modulesToAdd, String modulePath, String output) {
        this(System.getProperty("java.home"), modulesToAdd, modulePath, output);
    }

    public boolean apply() throws IOException {
        return runCommand(buildJLinkCommand());
    }

    @Override
    boolean wasSuccessful(CommandRunner.OutputStreams outputStreams) {
        boolean result = super.wasSuccessful(outputStreams);
        if(result){
            return !outputStreams.getSout().trim().startsWith("Error:");
        }
        return false;
    }

    private List<String> buildJLinkCommand() {
        List<String> command = new ArrayList<>();

        command.add(getJavaHome()+"/bin/"+"jlink");
        command.add("--verbose");
        command.add("--output");
        command.add(output == null ? defaultJlinkOutputArgument : output);
        command.add("--module-path");
        command.add(modulePath);
        command.add("--add-modules");
        command.add(String.join(",", modulesToAdd));
        command.addAll(defaultJlinkArguments);

        return command;
    }
}
