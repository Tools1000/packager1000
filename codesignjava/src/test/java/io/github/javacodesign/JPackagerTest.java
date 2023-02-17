package io.github.javacodesign;

import com.github.tools1000.SecretsLoader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class JPackagerTest {

    @BeforeEach
    void setUp() throws IOException {
        if(Files.exists(Paths.get("appdir")))
        try (Stream<? extends Path> dirStream = Files.walk(Paths.get("appdir"))) {
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
    void testApplyUnix01() throws IOException {

    }

    @Disabled
    @Test
    void testApplyWin01() throws IOException {

    }
}