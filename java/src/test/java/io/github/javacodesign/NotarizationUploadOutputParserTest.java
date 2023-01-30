package io.github.javacodesign;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class NotarizationUploadOutputParserTest {



    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void parse01() throws IOException {

        String sout = TestUtil.readString(Paths.get("src/test/resources/upload.json"));

        NotarizationUploadOutputParser parser = new NotarizationUploadOutputParser(sout, "");
        NotarizerResponse result = parser.parse();
        assertTrue(result.isUploadOk());
        assertEquals("c959508b-9b9f-445c-a556-d16b2994c2d3", result.getNotarizationUpload().requestUuid);

    }

    @Test
    void parse02() throws IOException {

        String sout = TestUtil.readString(Paths.get("src/test/resources/upload-error.json"));

        NotarizationUploadOutputParser parser = new NotarizationUploadOutputParser(sout, "");
        NotarizerResponse result = parser.parse();
       assertFalse(result.productErrors.isEmpty());

    }




}