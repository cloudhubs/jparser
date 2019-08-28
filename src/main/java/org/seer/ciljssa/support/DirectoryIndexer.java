package org.seer.ciljssa.support;

import java.io.File;
import java.util.Objects;

public class DirectoryIndexer {

    public interface FileHandler {
        void handle(int level, String path, File file);
    }

    public enum Language {
            JAVA, CSH, CPP, C, PYTHON, RUBY, OTHER
    }

    private FileHandler fileHandler;
    private Language language;

    public DirectoryIndexer(Language language, FileHandler fileHandler) {
        this.fileHandler = fileHandler;
        this.language = language;
    }

    public void explore(File root) {
        explore(0, "", root);
    }

    private void explore(int level, String path, File file) {
        if (file.isDirectory()) {
            for (File child : Objects.requireNonNull(file.listFiles())) {
                explore(level + 1, path + "/" + child.getName(), child);
            }
        } else {
            if (filter(file)) {
                fileHandler.handle(level, path, file);
            }
        }
    }

    private boolean filter(File file){
        switch(language) {
            case JAVA:
                if (file.getName().endsWith(".java")) return true; else return false;
        }
        return false;
    }
}