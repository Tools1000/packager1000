package io.github.javacodesign;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class NotarizerTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testNotarize01() throws IOException {
        Notarizer notarizer = new Notarizer("com.drkodi", "NMQU7U7Z5G", "3a8a5081-5288-41dd-8527-b6559074128a", Paths.get("../test/resources/DrKodi.app"));
        notarizer.submitNotarizationRequest();
    }

    @Test
    void testPoll01() throws IOException {
        Notarizer notarizer = new Notarizer("com.drkodi", "NMQU7U7Z5G", "3a8a5081-5288-41dd-8527-b6559074128a", Paths.get("../test/resources/DrKodi.app"));
        var result = notarizer.pollForNotarizationResult("cb23ab25-b46b-424a-b0bb-d4f7f9175947");
        assertTrue(result);
    }
}