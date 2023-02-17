package io.github.javacodesign;

import java.util.List;

public class LinuxRpmJPackager extends JPackager {

    @Override
    protected List<String> buildJPackagerCommand() {
        List<String> command = super.buildJPackagerCommand();
        command.add("--type");
        command.add("rpm");
        return command;
    }
}
