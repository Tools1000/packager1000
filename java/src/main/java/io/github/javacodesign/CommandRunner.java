package io.github.javacodesign;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class CommandRunner {

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    public static class OutputStreams {
        String sout;
        String serr;
    }



    public OutputStreams runCommand(List<String> command) throws IOException {
        return runCommand(command.toArray(new String[0]));
    }

    public OutputStreams runCommand(String[] command) throws IOException {
        log.debug("Running {}", Arrays.toString(command));
        StringBuilder soutBuilder = new StringBuilder();
        StringBuilder serrBuilder = new StringBuilder();
        Process process = Runtime.getRuntime().exec(command);
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            soutBuilder.append(line);
        }
        final BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String line2;
        while ((line2 = bufferedReader2.readLine()) != null) {
            serrBuilder.append(line2);
        }

        var result = new OutputStreams(soutBuilder.toString(), serrBuilder.toString());
        log.debug("Std out: {}", result.sout);
        log.debug("Std err: {}", result.serr);
        return result;
    }
}
