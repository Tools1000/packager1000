package io.github.javacodesign;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotarizationRequestOutputParserTest {

    String sout = "No errors uploading 'DrKodi.app.zip'.RequestUUID = cb23ab25-b46b-424a-b0bb-d4f7f9175947";

    String serrOk = "2023-01-26 17:55:26.617 *** Warning: altool has been deprecated for notarization and starting in late 2023 will no longer be supported by the Apple notary service. You should start using notarytool to notarize your software. (-1030)";

    String serr = " 2023-01-30 09:06:22.216 *** Error: Notarization failed for 'DrKodi.app.zip'.2023-01-30 09:06:22.217 *** Error: Unable to upload your app for notarization. Failed to authenticate with errors: (    \"Error Domain=ITunesConnectionAuthenticationErrorDomain Code=-26000 \\\"Failed to generate JWT token: Error Domain=NSCocoaErrorDomain Code=-43 \\\"Failed to load AuthKey file.\\\" UserInfo={NSLocalizedDescription=Failed to load AuthKey file., NSLocalizedFailureReason=The file \\U2018AuthKey_\\\"ABCDEFGHIJK\\\".p8\\U2019 could not be found in any of these locations: '~/sources/drkodi/private_keys', '~/private_keys', '~/.private_keys', '~/.appstoreconnect/private_keys'.}\\\" UserInfo={NSLocalizedRecoverySuggestion=Failed to generate JWT token: Error Domain=NSCocoaErrorDomain Code=-43 \\\"Failed to load AuthKey file.\\\" UserInfo={NSLocalizedDescription=Failed to load AuthKey file., NSLocalizedFailureReason=The file \\U2018AuthKey_\\\"ABCDEFGHIJK\\\".p8\\U2019 could not be found in any of these locations: '~/sources/drkodi/private_keys', '~/private_keys', '~/.private_keys', '~/.appstoreconnect/private_keys'.}, NSLocalizedDescription=Failed to generate JWT token: Error Domain=NSCocoaErrorDomain Code=-43 \\\"Failed to load AuthKey file.\\\" UserInfo={NSLocalizedDescription=Failed to load AuthKey file., NSLocalizedFailureReason=The file \\U2018AuthKey_\\\"ABCDEFGHIJK\\\".p8\\U2019 could not be found in any of these locations: '~/sources/drkodi/private_keys', '~/private_keys', '~/.private_keys', '~/.appstoreconnect/private_keys'.}, NSLocalizedFailureReason=Apple Services operation failed.}\") (-1011) {    NSLocalizedDescription = \"Unable to upload your app for notarization.\";    NSLocalizedFailureReason = \"Failed to authenticate with errors: (\\n    \\\"Error Domain=ITunesConnectionAuthenticationErrorDomain Code=-26000 \\\\\\\"Failed to generate JWT token: Error Domain=NSCocoaErrorDomain Code=-43 \\\\\\\"Failed to load AuthKey file.\\\\\\\" UserInfo={NSLocalizedDescription=Failed to load AuthKey file., NSLocalizedFailureReason=The file \\\\U2018AuthKey_\\\\\\\"ABCDEFGHIJK\\\\\\\".p8\\\\U2019 could not be found in any of these locations: '~/sources/drkodi/private_keys', '~/private_keys', '~/.private_keys', '~/.appstoreconnect/private_keys'.}\\\\\\\" UserInfo={NSLocalizedRecoverySuggestion=Failed to generate JWT token: Error Domain=NSCocoaErrorDomain Code=-43 \\\\\\\"Failed to load AuthKey file.\\\\\\\" UserInfo={NSLocalizedDescription=Failed to load AuthKey file., NSLocalizedFailureReason=The file \\\\U2018AuthKey_\\\\\\\"ABCDEFGHIJK\\\\\\\".p8\\\\U2019 could not be found in any of these locations: '~/sources/drkodi/private_keys', '~/private_keys', '~/.private_keys', '~/.appstoreconnect/private_keys'.}, NSLocalizedDescription=Failed to generate JWT token: Error Domain=NSCocoaErrorDomain Code=-43 \\\\\\\"Failed to load AuthKey file.\\\\\\\" UserInfo={NSLocalizedDescription=Failed to load AuthKey file., NSLocalizedFailureReason=The file \\\\U2018AuthKey_\\\\\\\"ABCDEFGHIJK\\\\\\\".p8\\\\U2019 could not be found in any of these locations: '~/sources/drkodi/private_keys', '~/private_keys', '~/.private_keys', '~/.appstoreconnect/private_keys'.}, NSLocalizedFailureReason=Apple Services operation failed.}\\\"\\n)\";}";

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void parse01() {
        NotarizationRequestOutputParser parser = new NotarizationRequestOutputParser(sout, serrOk);
        NotarizationRequestOutputParser.NotarizationOutputParserResult result = parser.parse();
        assertTrue(result.isOk());
        assertEquals("cb23ab25-b46b-424a-b0bb-d4f7f9175947", result.requestUuid);

    }

    @Test
    void parseError01() {
        NotarizationRequestOutputParser parser = new NotarizationRequestOutputParser(null, serr);
        NotarizationRequestOutputParser.NotarizationOutputParserResult result = parser.parse();
        assertFalse(result.isOk());


    }
}