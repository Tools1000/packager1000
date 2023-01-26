package io.github.javacodesign;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Getter
@Setter
@Slf4j
public class Zipper {

    final static int DEFAULT_BUFFER_SIZE = 1024;

    private int bufferSize;

    public Zipper(){
        this.bufferSize = DEFAULT_BUFFER_SIZE;
    }

    // zip a directory, including sub files and sub directories
    public Path zipFolder(Path source) throws IOException {

        // get folder name as zip file name
        String zipFileName = source.getFileName().toString() + ".zip";

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFileName))) {
            zos.putNextEntry(new ZipEntry(source.getFileName().toString()));
            Files.walkFileTree(source, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {

                    // only copy files, no symbolic links
                    if (attributes.isSymbolicLink()) {
                        return FileVisitResult.CONTINUE;
                    }

                    try (FileInputStream fis = new FileInputStream(file.toFile())) {

                        Path targetFile = source.relativize(file);
                        zos.putNextEntry(new ZipEntry(targetFile.toString()));

                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = fis.read(buffer)) > 0) {
                            zos.write(buffer, 0, len);
                        }
                        zos.closeEntry();
                        log.debug("Zip file: {}", file);

                    } catch (IOException e) {
                        log.error("Cannot zip file {}", file, e);
                        return FileVisitResult.TERMINATE;
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    log.error("Unable to zip: {}", file, exc);
                    return FileVisitResult.TERMINATE;
                }
            });
        }

        return Paths.get(zipFileName);
    }
}
