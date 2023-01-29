package io.github.javacodesign;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotarizationRequestOutputParserTest {

    String sout = "No errors uploading 'DrKodi.app.zip'.RequestUUID = cb23ab25-b46b-424a-b0bb-d4f7f9175947";

    String serr = "2023-01-26 17:55:26.617 *** Warning: altool has been deprecated for notarization and starting in late 2023 will no longer be supported by the Apple notary service. You should start using notarytool to notarize your software. (-1030)";

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void parse01() {
        NotarizationRequestOutputParser parser = new NotarizationRequestOutputParser(sout);
        NotarizationRequestOutputParser.NotarizationOutputParserResult result = parser.parse();
        assertEquals("cb23ab25-b46b-424a-b0bb-d4f7f9175947", result.requestUuid);

    }
}