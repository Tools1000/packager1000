package io.github.javacodesign;

import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
public class MacJPackager extends JPackager {

    private String macPackageIdentifier;

    private String macSigningKeyUserName;

    private String macEntitlements;

    public MacJPackager() {

    }

    protected List<String> buildJPackagerCommand() {
        List<String> command = super.buildJPackagerCommand();
        command.add("--type");
        command.add("dmg");
        if(macSigningKeyUserName != null && !macSigningKeyUserName.isEmpty()){
            command.add("--mac-sign");
            command.add("--mac-signing-key-user-name");
            command.add(macSigningKeyUserName);
        }
        if(macEntitlements != null && !macEntitlements.isEmpty()){
            command.add("--mac-entitlements");
            command.add(macEntitlements);
        }
        if(macPackageIdentifier != null && !macPackageIdentifier.isEmpty()) {
            command.add("--mac-package-identifier");
            command.add(macPackageIdentifier);
        }
        return command;
    }

    // Getter / Setter //

    public String getMacPackageIdentifier() {
        return macPackageIdentifier;
    }

    public MacJPackager setMacPackageIdentifier(String macPackageIdentifier) {
        this.macPackageIdentifier = macPackageIdentifier;
        return this;
    }

    public String getMacSigningKeyUserName() {
        return macSigningKeyUserName;
    }

    public MacJPackager setMacSigningKeyUserName(String macSigningKeyUserName) {
        this.macSigningKeyUserName = macSigningKeyUserName;
        return this;
    }

    public String getMacEntitlements() {
        return macEntitlements;
    }

    public MacJPackager setMacEntitlements(String macEntitlements) {
        this.macEntitlements = macEntitlements;
        return this;
    }

    @Override
    public MacJPackager setName(String name) {
        super.setName(name);
        return this;
    }

    @Override
    public MacJPackager setAppVersion(String appVersion) {
        super.setAppVersion(appVersion);
        return this;
    }

    @Override
    public MacJPackager setModule(String module) {
        super.setModule(module);
        return this;
    }

    @Override
    public MacJPackager setInput(String input) {
        super.setInput(input);
        return this;
    }

    @Override
    public MacJPackager setModulePath(List<String> modulePath) {
        super.setModulePath(modulePath);
        return this;
    }

    @Override
    public MacJPackager setRuntimeImage(String runtimeImage) {
        super.setRuntimeImage(runtimeImage);
        return this;
    }

    @Override
    public MacJPackager setDest(String dest) {
        super.setDest(dest);
        return this;
    }

    @Override
    public MacJPackager setIcon(String icon) {
         super.setIcon(icon);
         return this;
    }
}
