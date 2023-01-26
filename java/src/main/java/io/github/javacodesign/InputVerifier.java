package io.github.javacodesign;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class InputVerifier {

    protected void verifyInput(Path... paths) throws IOException {
        for(Path path : paths){
            checkExists(path);
            checkCanRead(path);
        }
    }

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
