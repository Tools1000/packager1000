package packager1000.libs;

import com.github.tools1000.SecretsLoader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;

class SignerTest {

    private String identity;

    @BeforeEach
    void setUp() throws IOException {
        identity = new SecretsLoader().getSecret("developer.id");
    }

    @AfterEach
    void tearDown() {
    }

    @Disabled
    @Test
    void test01() throws IOException {



        Signer signer = new Signer(identity, Paths.get("../test/resources/DrKodi.app"), Paths.get("../test/resources/DrKodi.app/Contents/MacOS/launcher-macosx-x86_64.sh"), Paths.get("../entitlements.plist"));

        signer.removeSignature();
        signer.sign();
//        codeSigner.removeSignature();
    }

    @Test
    void test02() throws IOException {
        new Signer(identity, Paths.get("../test/resources/DrKodi.app"), Paths.get("../test/resources/DrKodi.app/Contents/MacOS/launcher-macosx-x86_64.sh"));
    }

    @Disabled
    @Test
    void test03() throws IOException {
        new Signer(identity, Paths.get("appdir/DrKodi.app"), Paths.get("appdir/DrKodi.app/Contents/MacOS/DrKodi")).sign();
    }

}