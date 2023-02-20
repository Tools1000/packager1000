package io.github.javacodesign;

import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
public class MacJPackager extends JPackager {

    private String macPackageIdentifier;

    private String macSigningKeyUserName;

    private String macEntitlements;

    private String macPackageName;

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
        if(macPackageName != null && !macPackageName.isEmpty()){
            command.add("--mac-package-name");
            command.add(macPackageName);
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

    public String getMacPackageName() {
        return macPackageName;
    }

    public MacJPackager setMacPackageName(String macPackageName) {
        this.macPackageName = macPackageName;
        return this;
    }
}
