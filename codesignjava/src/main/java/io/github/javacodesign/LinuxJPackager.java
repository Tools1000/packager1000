package io.github.javacodesign;

import java.util.List;

public class LinuxJPackager extends JPackager {

    @Override
    protected List<String> buildJPackagerCommand() {
        List<String> command = super.buildJPackagerCommand();
        command.add("--type");
        command.add("app-image");
        return command;
    }
}
