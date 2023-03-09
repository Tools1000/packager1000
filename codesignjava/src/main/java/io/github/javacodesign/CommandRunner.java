package io.github.javacodesign;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
public class CommandRunner extends InputVerifier {

    public boolean runCommand(List<String> command) throws IOException {
        com.github.tools1000.CommandRunner commandRunner = new com.github.tools1000.CommandRunner();
        com.github.tools1000.CommandRunner.OutputStreams result = commandRunner.runCommand(command);
        boolean success = wasSuccessful(result);
        if(!success){
            log.warn("Command {} finished with errors: {}", String.join(" ", command), result.getSerr());
        }
        return true;
    }

    boolean wasSuccessful(com.github.tools1000.CommandRunner.OutputStreams outputStreams) {
        return outputStreams.getSerr() == null || outputStreams.getSerr().isEmpty();
    }
}
