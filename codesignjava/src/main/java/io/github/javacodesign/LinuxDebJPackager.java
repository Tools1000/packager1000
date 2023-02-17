package io.github.javacodesign;

import java.util.List;

public class LinuxDebJPackager extends JPackager {

    @Override
    protected List<String> buildJPackagerCommand() {
        List<String> command = super.buildJPackagerCommand();
        command.add("--type");
        command.add("deb");
        return command;
    }
}
