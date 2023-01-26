package io.github.javacodesign;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;

class SignerTest {

    private String identity;

    @BeforeEach
    void setUp() {
        identity = "Developer ID Application: Alexander Kerner (5W26Y2F3CM)";
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void test01() throws IOException {
        Signer signer = new Signer(identity, Paths.get("../test/resources/DrKodi.app"), Paths.get("../test/resources/DrKodi.app/Contents/MacOS/launcher-macosx-x86_64.sh"), Paths.get("../entitlements-jvm.plist"), Paths.get("../entitlements-launcher.plist"));

        signer.removeSignature();
        signer.sign();
//        codeSigner.removeSignature();
    }
}