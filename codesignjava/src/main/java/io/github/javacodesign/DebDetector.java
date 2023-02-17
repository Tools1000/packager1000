package io.github.javacodesign;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DebDetector extends CommandRunner {

    public boolean apply() throws IOException {
        return runCommand(buildCommand());
    }

    private List<String> buildCommand() {
        List<String> command = new ArrayList<>();
        command.add("dpkg");
        command.add("-l");
        return command;
    }


}
