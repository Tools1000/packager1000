package packager1000.libs;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class Notarizer extends JavaCommandRunner {

    private final Path inputPath;

    private final String keychainProfile;

    public boolean notarize() throws IOException {
        verifyInput(this.inputPath);
        verifyInput(this.keychainProfile);
        return runCommand(buildNotarizeCommand());
    }

    private List<String> buildNotarizeCommand() {
        List<String> result = new ArrayList<>();

        result.add("xcrun");
        result.add("notarytool");
        result.add("submit");
        result.add(inputPath.toString());
        result.add("--wait");
        result.add("--keychain-profile");
        result.add(keychainProfile);

        return result;
    }
}
