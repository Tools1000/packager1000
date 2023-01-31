package io.github.javacodesign;

import com.github.tools1000.SecretsLoader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class NotarizerTest {

    static Properties secrets;

    @BeforeAll
    static void init() throws IOException {
        secrets = new SecretsLoader().load().getProperties();
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testsubmitNotarizationRequest01() throws IOException {
        Notarizer notarizer = new Notarizer("com.drkodi", secrets.getProperty("notarization.api.key"), secrets.getProperty("notarization.api.issuer"), Paths.get("../test/resources/DrKodi.app"));
        notarizer.notarizationUpload();
    }

    @Test
    void testPoll01() throws IOException {
        Notarizer notarizer = new Notarizer("com.drkodi", secrets.getProperty("notarization.api.key"), secrets.getProperty("notarization.api.issuer"), Paths.get("../test/resources/DrKodi.app"));
        NotarizerResponse result = notarizer.getNotarizationInfo("cb23ab25-b46b-424a-b0bb-d4f7f9175947");
        assertNotNull(result.getNotarizationInfo());
    }

    @Test
    void testNotarize01() throws IOException {
        Notarizer notarizer = new Notarizer("com.drkodi", secrets.getProperty("notarization.api.key"), secrets.getProperty("notarization.api.issuer"), Paths.get("../test/resources/DrKodi.app"));
        boolean result = notarizer.notarize(0, 0);

    }
}