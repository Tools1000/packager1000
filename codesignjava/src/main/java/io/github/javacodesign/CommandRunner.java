package io.github.javacodesign;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
public class CommandRunner extends InputVerifier {

    public boolean runCommand(List<String> command) throws IOException {
        com.github.tools1000.CommandRunner commandRunner = new com.github.tools1000.CommandRunner();
        com.github.tools1000.CommandRunner.OutputStreams result = commandRunner.runCommand(command);
        if(result.getSerr() != null && !result.getSerr().isEmpty()){
            log.warn("Command {} finished with errors: {}", String.join(" ", command), result.getSerr());
        }
        return result.getSerr().isEmpty();
    }
}
