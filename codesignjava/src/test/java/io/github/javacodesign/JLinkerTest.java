package io.github.javacodesign;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class JLinkerTest {

    @BeforeEach
    void setUp() throws IOException {
        if(Files.exists(Paths.get("jre")))
        try (Stream<? extends Path> dirStream = Files.walk(Paths.get("jre"))) {
            dirStream
                    .map(Path::toFile)
                    .sorted(Comparator.reverseOrder())
                    .forEach(File::delete);
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Disabled
    @Test
    void testApply01() throws IOException {
        JLinker jLinker = new JLinker(Arrays.asList("java.desktop", "java.base", "java.logging", "java.xml", "java.scripting", "java.compiler", "java.instrument", "jdk.unsupported", "javafx.base", "javafx.graphics","javafx.controls","javafx.fxml"), Paths.get("/Users/alex/sources/drkodi/target/mods").toString(), null) ;
        assertTrue(jLinker.apply());
    }
}