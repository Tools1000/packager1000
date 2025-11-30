package packager1000.libs;

import java.util.List;

public class WindowsJPackager extends JPackager {

    private String winUpgradeUuid;

    @Override
    protected List<String> buildJPackagerCommand() {
        List<String> command = super.buildJPackagerCommand();
        command.add("--type");
        command.add("exe");
        command.add(getName());
        return command;
    }

    // Getter / Setter //

    public String getWinUpgradeUuid() {
        return winUpgradeUuid;
    }

    public WindowsJPackager setWinUpgradeUuid(String winUpgradeUuid) {
        this.winUpgradeUuid = winUpgradeUuid;
        return this;
    }
}
