package edu.baylor.ecs.ciljssa.factory.directory;

import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.component.impl.DirectoryComponent;
import edu.baylor.ecs.ciljssa.context.AnalysisContext;
import edu.baylor.ecs.ciljssa.model.InstanceType;

import java.io.File;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DirectoryFactory {

    /**
     * Create directory graph from a path
     * @param path
     * @return The parent component in the directory graph, null if could not be created or was not directory
     */
    public Component createDirectoryGraph(String path) {
        return createDirectoryGraph(null, path);
    }

    private DirectoryComponent createDirectoryGraph(Component parent, String path) {
        DirectoryComponent output = createDirectoryComponent(parent, path);
        return getComponent(path, output);
    }

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
                if (f.getName().endsWith(".java")) { // TODO: Java specific
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
