package packager1000.libs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NotarizationStapler extends CommandRunner {

    private String dmgPath;

    public boolean apply() throws IOException {
        return runCommand(buildCommand());
    }

    private List<String> buildCommand() {
        List<String> command = new ArrayList<>();
        command.add("xcrun");
        command.add("stapler");
        command.add("staple");
        command.add(dmgPath);
        return command;
    }

    public String getDmgPath() {
        return dmgPath;
    }

    public NotarizationStapler setDmgPath(String dmgPath) {
        this.dmgPath = dmgPath;
        return this;
    }
}
