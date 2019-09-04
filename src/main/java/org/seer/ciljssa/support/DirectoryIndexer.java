package org.seer.ciljssa.support;

import lombok.Data;

import java.io.File;
import java.util.Objects;

@Data
public class DirectoryIndexer {

    public interface IFileHandler {
        Object handle(int level, String path, File file);
    }

    public enum Language {
            JAVA, CSH, CPP, C, PYTHON, RUBY, OTHER
    }

    private IFileHandler fileHandler;
    private Language language;

    public DirectoryIndexer(Language language, IFileHandler fileHandler) {
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
                return file.getName().endsWith(".java");
            case CSH:
            case CPP:
            case C:
            case PYTHON:
            case RUBY:
            case OTHER:
            default:
                return false;
        }
    }
}