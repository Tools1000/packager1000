package packager1000.libs;

import java.util.List;

public class LinuxRpmJPackager extends LinuxJPackager {

    @Override
    protected List<String> buildJPackagerCommand() {
        List<String> command = super.buildJPackagerCommand();
        command.add("--type");
        command.add("rpm");
        return command;
    }
}
