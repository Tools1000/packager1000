package packager1000.libs;

import java.util.List;

public class LinuxDebJPackager extends LinuxJPackager {

    private String linuxDebMaintainer;

    @Override
    protected List<String> buildJPackagerCommand() {
        List<String> command = super.buildJPackagerCommand();
        command.add("--type");
        command.add("deb");
        if(linuxDebMaintainer != null && !linuxDebMaintainer.isEmpty()){
            command.add("--linux-deb-maintainer");
            command.add(linuxDebMaintainer);
        }
        return command;
    }

    public String getLinuxDebMaintainer() {
        return linuxDebMaintainer;
    }

    public LinuxDebJPackager setLinuxDebMaintainer(String linuxDebMaintainer) {
        this.linuxDebMaintainer = linuxDebMaintainer;
        return this;
    }
}
