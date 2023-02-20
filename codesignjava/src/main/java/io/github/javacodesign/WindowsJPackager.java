package io.github.javacodesign;

import java.util.List;

public class WindowsJPackager extends JPackager {

    private String winUpgradeUuid;

    @Override
    protected List<String> buildJPackagerCommand() {
        List<String> command = super.buildJPackagerCommand();
        command.add("--type");
        command.add("msi");
        command.add("--win-dir-chooser");
        command.add("--win-menu");
        command.add("--win-menu-group");
        command.add(getName());
        command.add("--win-shortcut");
        command.add("--win-upgrade-uuid");
        command.add(winUpgradeUuid);
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
