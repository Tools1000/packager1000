package io.github.javacodesign;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotarizationResultOutputParserTest {

    String sout = "No errors getting notarization info.\n" +
            "\n" +
            "          Date: 2023-01-26 16:55:28 +0000\n" +
            "          Hash: 34197eb7b84e0c692cd4dc9844e1ec9d04350e1e668dc71285f8a79e70d3c0dd\n" +
            "    LogFileURL: https://osxapps-ssl.itunes.apple.com/itunes-assets/Enigma113/v4/90/00/64/9000644c-3afd-aaf5-4a5f-99fb08c49a43/developer_log.json?accessKey=1674948765_4802964957527093599_gwqnBCtWQdsfyGPHSkS4U9QQRofgEsbNhR0KtwxPWQTu2Anq4IgvbTkPKQmiC4aNs6N2P2QC2lFX4XcwZwjLTBBy0KFBkbAJ73oc9wkzIy%2BB1Bu9rOetGNKssukTEfs7AX%2BdYJOHrF6LL4xehTKVzVjD3ALGaTBSdgOoUfXwQpA%3D\n" +
            "   RequestUUID: cb23ab25-b46b-424a-b0bb-d4f7f9175947\n" +
            "        Status: success\n" +
            "   Status Code: 0\n" +
            "Status Message: Package Approved\n";

    String sout2 = "No errors getting notarization info.       Date: 2023-01-30 08:33:30 +0000RequestUUID: 8e8cb5f0-aff0-4185-9a67-6a2781b5774d     Status: in progressStatus Code: 0";

    String serr = "2023-01-26 18:48:20.793 *** Warning: altool has been deprecated for notarization and starting in late 2023 will no longer be supported by the Apple notary service. You should start using notarytool to notarize your software. (-1030)";

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void parse01() {
        NotarizationResultOutputParser parser = new NotarizationResultOutputParser(sout, serr);
        NotarizationResultOutputParser.NotarizationOutputParserResult result = parser.parse();
        assertEquals(NotarizationResultOutputParser.NotarizationOutputParserResult.Status.SUCCESS, result.status);
    }

    @Test
    void parse02() {
        NotarizationResultOutputParser parser = new NotarizationResultOutputParser(sout2, serr);
        NotarizationResultOutputParser.NotarizationOutputParserResult result = parser.parse();
        assertEquals(NotarizationResultOutputParser.NotarizationOutputParserResult.Status.IN_PROGRESS, result.status);
    }
}