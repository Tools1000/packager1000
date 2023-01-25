package javacodesign;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class CommandRunner {

    public void runCommand(List<String> command) {
        runCommand(command.toArray(new String[0]));
    }

    public void runCommand(String[] command) {
        log.debug("Running {}", Arrays.toString(command));
        try {
            Process process = Runtime.getRuntime().exec(command);
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                log.debug("sout: {}", line);
            }
            final BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line2;
            while ((line2 = bufferedReader2.readLine()) != null) {
                log.debug("serr: {}", line2);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
