package io.github.javacodesign;

import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class InputVerifier {

    /**
     * Checks if provided paths exist and are readable.
     * @param paths Paths to check
     * @throws FileNotFoundException if one provided paths does not exist
     * @throws IOException if one of provided paths can not be read
     */
    protected void verifyInput(Path... paths) throws IOException {
        for(Path path : paths){
            checkExists(path);
            checkCanRead(path);
        }
    }

    /**
     * Checks if provided strings are not null and not empty.
     * @param strings Strings to check
     * @throws IllegalArgumentException if one of provided strings is null or empty
     */
    protected void verifyInput(String... strings) {
        for(String s : strings){
            if(s == null || s.isEmpty()){
                throw new IllegalArgumentException("Invalid input string");
            }
        }
    }

    /**
     * Checks if provided strings are not null and not empty.
     * @param strings Strings to check
     * @throws IllegalArgumentException if one of provided strings is null or empty
     */
    protected void verifyInput(List<String> strings) throws IOException {
        verifyInput(strings.toArray(new String[0]));
    }

    /**
     * Checks if a path exists.
     * @param path Path to check
     * @throws FileNotFoundException if given path does not exist
     */
    protected void checkExists(Path path) throws FileNotFoundException {
        if(!Files.exists(path)){
            throw new FileNotFoundException(path.toString());
        }
    }

    protected void checkCanRead(Path path) throws IOException {
        if(!Files.isReadable(path)){
            throw new IOException("Cannot read " + path);
        }
    }

    protected void checkCanExecute(Path path) throws IOException {
        if(!Files.isExecutable(path)){
            throw new IOException("Cannot execute " + path);
        }
    }
}
