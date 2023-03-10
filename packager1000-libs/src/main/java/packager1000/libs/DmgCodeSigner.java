package packager1000.libs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DmgCodeSigner extends CommandRunner {

    private String developerId;

    private String dmgPath;

    public boolean apply() throws IOException {
        return runCommand(buildCommand());
    }

    private List<String> buildCommand() {
        List<String> command = new ArrayList<>();
        command.add("codesign");
        command.add("--timestamp");
        command.add("-s");
        command.add(developerId);
        command.add(dmgPath);
        return command;
    }

    public String getDeveloperId() {
        return developerId;
    }

    public DmgCodeSigner setDeveloperId(String developerId) {
        this.developerId = developerId;
        return this;
    }

    public String getDmgPath() {
        return dmgPath;
    }

    public DmgCodeSigner setDmgPath(String dmgPath) {
        this.dmgPath = dmgPath;
        return this;
    }
}
