package io.github.javacodesign;

import com.github.tools1000.SecretsLoader;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class NotarizerTest {



    @BeforeAll
    static void init() throws IOException {

    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Disabled
    @Test
    void testsubmitNotarizationRequest01() throws IOException {
        Notarizer notarizer = new Notarizer("com.drkodi", new SecretsLoader().getSecret("notarization.api.key"), new SecretsLoader().getSecret("notarization.api.issuer"), Paths.get("../test/resources/DrKodi.app"));
        notarizer.notarizationUpload();
    }

    @Disabled
    @Test
    void testPoll01() throws IOException {
        Notarizer notarizer = new Notarizer("com.drkodi", new SecretsLoader().getSecret("notarization.api.key"), new SecretsLoader().getSecret("notarization.api.issuer"), Paths.get("../test/resources/DrKodi.app"));
        NotarizerResponse result = notarizer.getNotarizationInfo("cb23ab25-b46b-424a-b0bb-d4f7f9175947");
        assertNotNull(result.getNotarizationInfo());
    }

    @Disabled
    @Test
    void testNotarize01() throws IOException {
        Notarizer notarizer = new Notarizer("com.drkodi", new SecretsLoader().getSecret("notarization.api.key"), new SecretsLoader().getSecret("notarization.api.issuer"), Paths.get("../test/resources/DrKodi.app"));
        boolean result = notarizer.notarize(0, 0);

    }
}