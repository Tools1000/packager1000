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

    // Override //


    @Override
    public WindowsJPackager setName(String name) {
        super.setName(name);
        return this;
    }

    @Override
    public WindowsJPackager setAppVersion(String appVersion) {
        super.setAppVersion(appVersion);
        return this;
    }

    @Override
    public WindowsJPackager setModule(String module) {
        super.setModule(module);
        return this;
    }

    @Override
    public WindowsJPackager setInput(String input) {
        super.setInput(input);
        return this;
    }

    @Override
    public WindowsJPackager setModulePath(List<String> modulePath) {
        super.setModulePath(modulePath);
        return this;
    }

    @Override
    public WindowsJPackager setRuntimeImage(String runtimeImage) {
        super.setRuntimeImage(runtimeImage);
        return this;
    }

    @Override
    public WindowsJPackager setDest(String dest) {
        super.setDest(dest);
        return this;
    }

    @Override
    public WindowsJPackager setIcon(String icon) {
        super.setIcon(icon);
        return this;
    }
}
