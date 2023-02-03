package io.github.javacodesign;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class NotarizationInfoOutputParserTest {



    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void parse01() throws IOException {

        String sout = TestUtil.readString(Paths.get("src/test/resources/info.json"));

        NotarizationInfoOutputParser parser = new NotarizationInfoOutputParser(sout);
        NotarizerResponse result = parser.parse();
        assertEquals("success", result.getNotarizationInfo().status);
    }


}