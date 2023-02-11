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
        JPackager jPackager = JPackager.builder()
                .module("drkodi/drkodi.Launcher")
                .name("DrKodi")
                .appVersion("1.0.0")
                .dest("appdir")
                .macPackageIdentifier("com.drkodi")
                .modulePath(Collections.singletonList(Paths.get("/Users/alex/sources/drkodi/target/libs").toString()))
                .runtimeImage(Paths.get("jre").toString())
                .signingKeyUserName(new SecretsLoader().getSecret("developer.id"))
                .build();
        assertTrue(jPackager.apply());
    }

    @Disabled
    @Test
    void testApplyWin01() throws IOException {
        JPackager jPackager = JPackager.builder()
                .module("drkodi/drkodi.Launcher")
                .name("DrKodi")
                .appVersion("1.0.0")
                .dest("appdir")
//                .macPackageIdentifier("com.drkodi")
                .modulePath(Collections.singletonList(Paths.get("C:\\Users\\Administrator\\sources\\drkodi\\target\\libs").toString()))
                .runtimeImage(Paths.get("jre").toString())
                .build();
        assertTrue(jPackager.apply());
    }
}