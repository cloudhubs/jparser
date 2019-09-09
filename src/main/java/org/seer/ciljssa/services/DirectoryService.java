package org.seer.ciljssa.services;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.Arrays;

@Data
@NoArgsConstructor
public class DirectoryService {

    public ArrayList<File> getFilesFromDirectory(String path) throws NotDirectoryException {
        ArrayList<File> files = new ArrayList<>();
        File file = new File(path);
        if(file.isDirectory()) {
            Arrays.stream(file.listFiles()).forEach(f->{
                if (f.getName().endsWith(".java")) {
                    files.add(f);
                }
            });
            return files;
        } else {
            throw new NotDirectoryException(path);
        }
    }

}
