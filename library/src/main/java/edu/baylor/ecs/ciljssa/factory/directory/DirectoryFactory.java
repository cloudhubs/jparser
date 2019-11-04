package edu.baylor.ecs.ciljssa.factory.directory;

import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.component.impl.DirectoryComponent;
import edu.baylor.ecs.ciljssa.model.InstanceType;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DirectoryFactory {

    public static final String DEFAULT_LANGUAGE = "Java";

    private String lang;

    public DirectoryFactory(String language) {
        this.lang = language;
    }

    public DirectoryFactory() {
        this(DEFAULT_LANGUAGE);
    }

    /**
     * Create directory graph from a path
     * @param path
     * @return The parent component in the directory graph, null if could not be created or was not directory
     */
    public Component createDirectoryGraph(String path) {
        return createDirectoryGraph(null, path);
    }

    /**
     * Entry point to recursive stepping algorithm for creating directory graph
     * @param parent
     * @param path
     * @return
     */
    private DirectoryComponent createDirectoryGraph(Component parent, String path) {
        DirectoryComponent output = createDirectoryComponent(parent, path);
        return getComponent(path, output);
    }

    /**
     * Recursive algorithm, sets parent components, forms tree of directories and their files.
     * @param path Path to component
     * @param output Parent directory component
     * @return
     */
    private DirectoryComponent getComponent(String path, DirectoryComponent output) {
        List<File> files = new ArrayList<>();
        List<DirectoryComponent> subDirectories = new ArrayList<>();
        File file = new File(path);
        if(file.isDirectory()) {
            Arrays.stream(Objects.requireNonNull(file.listFiles())).forEach(f -> {
                if (f.isDirectory()) {
                    try {
                        DirectoryComponent sub = createDirectoryGraph(output, f.getPath());
                        subDirectories.add(sub);
                    } catch (NullPointerException e) {
                        System.out.println("NullPointerException in DirectoryFactory");
                    }
                }
                if (fileTypeMatchesLanguage(f)) {
                    files.add(f);
                }
            });
            output.setFiles(files);
            output.setSubDirectories(subDirectories);
            if (subDirectories.size() == 0) output.setHasSubDirectories(false); else output.setHasSubDirectories(true);
            return output;
        } else {
            return null;
        }
    }

    /**
     * Does the given file match the factory's requested language type? Currently only supports Java.
     * @param f
     * @return
     */
    private boolean fileTypeMatchesLanguage(File f) {
        if (lang.equalsIgnoreCase("java")) {
            return f.getName().endsWith(".java");
        }
        return false;
    }

    /**
     * Creates a basically empty DirectoryComponent object for the use of the createDirectoryGraph method
     * @param parent Parent component
     * @param path
     * @return
     */
    private DirectoryComponent createDirectoryComponent(Component parent, String path) {
        DirectoryComponent output = new DirectoryComponent(path);
        output.setInstanceName(path + "::DirectoryComponent");
        output.setPackageName(path + "::PackageName");
        output.setInstanceType(InstanceType.DIRECTORYCOMPONENT);
        output.setParent(parent);
        return output;
    }

}