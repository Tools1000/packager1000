package io.github.javacodesign;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;

class CodeSignerTest {

    private String identity;

    @BeforeEach
    void setUp() {
        identity = "Developer ID Application: John Doe (1234567890)";
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void test01() throws IOException {
        CodeSigner codeSigner = new CodeSigner(identity, Paths.get("../test/resources/DrKodi.app"), Paths.get("../test/resources/DrKodi.app/Contents/MacOS/launcher-macosx-x86_64.sh"), Paths.get("../entitlements-jvm.plist"), Paths.get("../entitlements-launcher.plist"));

        codeSigner.removeSignature();
        codeSigner.sign();
//        codeSigner.removeSignature();
    }
}