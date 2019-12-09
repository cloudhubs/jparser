package edu.baylor.ecs.cloudhubs.jparser.app.services;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Service
public class DirectoryService {

    public List<File> getFilesFromDirectorySmart(String path) throws NotDirectoryException {
        List<File> files;
        File file = new File(path);
        if(file.isDirectory()) {
            files = getFilesFromDirectoryAndSubdirectory(path);
            return files;
        } else {
            throw new NotDirectoryException(path);
        }
    }

    private List<File> getFilesFromDirectoryAndSubdirectory(String path) {
        List<File> files = new ArrayList<>();
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
