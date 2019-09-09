package org.seer.ciljssa.services;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

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

    public ArrayList<File> getFilesFromDirectorySmart(String path) throws NotDirectoryException {
        ArrayList<File> files = new ArrayList<>();
        File file = new File(path);
        if(file.isDirectory()) {
            files = getFilesFromDirectoryAndSubdirectory(path);
            return files;
        } else {
            throw new NotDirectoryException(path);
        }
    }

    private ArrayList<File> getFilesFromDirectoryAndSubdirectory(String path) {
        ArrayList<File> files = new ArrayList<>();
        File file = new File(path);
        if(file.isDirectory()) {
            Arrays.stream(file.listFiles()).forEach(f -> {
                if (f.isDirectory()) {
                    try {
                        files.addAll(getFilesFromDirectoryAndSubdirectory(f.getPath()).stream().filter(File::exists)
                                .collect(Collectors.toCollection(ArrayList::new)));
                    } catch (NullPointerException e) {
                        System.out.println("NullPointerException in Directory service");
                    }
                }
                if (f.getName().endsWith(".java")) {
                    files.add(f);
                }
            });
            return files;
        } else {
            return null;
        }
    }

}
