package io.github.javacodesign;

import com.github.tools1000.CommandRunner;
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

import static org.junit.jupiter.api.Assertions.assertFalse;
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
    void testApplyUnix01() throws IOException {
        JLinker jLinker = new JLinker(Arrays.asList("java.desktop", "java.base", "java.logging", "java.xml", "java.scripting", "java.compiler", "java.instrument", "jdk.unsupported", "javafx.base", "javafx.graphics","javafx.controls","javafx.fxml", "java.sql", "java.naming"), Paths.get("/Users/alex/sources/drkodi/target/mods").toString(), null);
        assertTrue(jLinker.apply());
    }

    @Disabled
    @Test
    void testApplyWin01() throws IOException {
        JLinker jLinker = new JLinker(Arrays.asList("java.desktop", "java.base", "java.logging", "java.xml", "java.scripting", "java.compiler", "java.instrument", "jdk.unsupported", "javafx.base", "javafx.graphics","javafx.controls","javafx.fxml", "java.sql", "java.naming"),
                Paths.get("C:\\Users\\Administrator\\sources\\drkodi\\target\\mods").toString(), null) ;
        assertTrue(jLinker.apply());
    }

    @Test
    void testError01(){
        String out = "Error: Module java.net not foundjava.lang.module.FindException: Module java.net not found\tat java.base/java.lang.module.Resolver.findFail(Resolver.java:893)\tat java.base/java.lang.module.Resolver.resolve(Resolver.java:129)\tat java.base/java.lang.module.Configuration.resolve(Configuration.java:421)\tat java.base/java.lang.module.Configuration.resolve(Configuration.java:255)\tat jdk.jlink/jdk.tools.jlink.internal.Jlink$JlinkConfiguration.resolve(Jlink.java:217)\tat jdk.jlink/jdk.tools.jlink.internal.JlinkTask.createImageProvider(JlinkTask.java:536)\tat jdk.jlink/jdk.tools.jlink.internal.JlinkTask.createImage(JlinkTask.java:424)\tat jdk.jlink/jdk.tools.jlink.internal.JlinkTask.run(JlinkTask.java:276)\tat jdk.jlink/jdk.tools.jlink.internal.Main.run(Main.java:55)\tat jdk.jlink/jdk.tools.jlink.internal.Main.main(Main.java:33)";

        boolean successful = new JLinker(null,null,null).wasSuccessful(new CommandRunner.OutputStreams(out, null));

        assertFalse(successful);
    }
}