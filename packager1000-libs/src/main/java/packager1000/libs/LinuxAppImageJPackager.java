package packager1000.libs;

import java.util.List;

public class LinuxAppImageJPackager extends JPackager {

    @Override
    protected List<String> buildJPackagerCommand() {
        List<String> command = super.buildJPackagerCommand();
        command.add("--type");
        command.add("app-image");
        return command;
    }
}
