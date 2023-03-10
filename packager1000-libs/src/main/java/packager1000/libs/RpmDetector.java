package packager1000.libs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RpmDetector extends CommandRunner {

    public boolean apply() throws IOException {
        return runCommand(buildCommand());
    }

    private List<String> buildCommand() {
        List<String> command = new ArrayList<>();
        command.add("rpm");
        command.add("-qa");
        return command;
    }


}
