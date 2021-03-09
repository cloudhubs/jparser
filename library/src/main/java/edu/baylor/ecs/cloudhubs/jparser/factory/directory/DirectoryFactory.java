package edu.baylor.ecs.cloudhubs.jparser.factory.directory;

import edu.baylor.ecs.cloudhubs.jparser.component.Component;
import edu.baylor.ecs.cloudhubs.jparser.component.impl.DirectoryComponent;
import edu.baylor.ecs.cloudhubs.jparser.model.InstanceType;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DirectoryFactory {

    public static final DirectoryComponent DEFAULT_PARENT = initializeDefaultParent();
    public static final String DEFAULT_PARENT_PATH = "DEFAULT_PARENT_PATH";
    public static final String DEFAULT_PARENT_INSTANCE_NAME = "DEFAULT_PARENT_INSTANCE_NAME";

//    public static final String DEFAULT_LANGUAGE = "Java";
//    private String lang;
//    public DirectoryFactory(String language) {
//        this.lang = language;
//    }
//    public DirectoryFactory() {
//        this(DEFAULT_LANGUAGE);
//    }

    /**
     * Sets up the default parent component so that no DirectoryComponent created in this factory have a null parent,
     * except for this one.
     * @return
     */
    private static DirectoryComponent initializeDefaultParent() {
        DirectoryComponent defaultComponent = new DirectoryComponent();
        defaultComponent.setInstanceType(InstanceType.DIRECTORYCOMPONENT);
        defaultComponent.setPath(DEFAULT_PARENT_PATH);
        defaultComponent.setInstanceName(DEFAULT_PARENT_INSTANCE_NAME);
        return defaultComponent;
    }

    /**
     * Create directory graph from a path
     * @param path
     * @return The parent component in the directory graph, null if could not be created or was not directory
     */
    public Component createDirectoryGraph(String path, List<String> ignorePaths) {
        return createDirectoryGraph(DEFAULT_PARENT, path, ignorePaths);
    }

    public Component createDirectoryGraphOfFile(File file) {
        DirectoryComponent output = createDirectoryComponent(DEFAULT_PARENT, file.getParentFile().getPath());
        output.addFile(file.getAbsolutePath());
        return output;
    }

    /**
     * Entry point to recursive stepping algorithm for creating directory graph
     * @param parent
     * @param path
     * @return
     */
    private DirectoryComponent createDirectoryGraph(Component parent, String path, List<String> ignorePaths) {
        DirectoryComponent output = createDirectoryComponent(parent, path);
        return getComponent(path, output, ignorePaths);
    }

    /**
     * Recursive algorithm, sets parent components, forms tree of directories and their files.
     * @param path Path to component
     * @param output Parent directory component
     * @return
     */
    private DirectoryComponent getComponent(String path, DirectoryComponent output, List<String> ignorePaths) {
        List<String> files = new ArrayList<>();
        List<DirectoryComponent> subDirectories = new ArrayList<>();
        File file = new File(path);
        if(file.isDirectory()) {
            Arrays.stream(Objects.requireNonNull(file.listFiles())).forEach(f -> {
                if (f.isDirectory()) {
                    if (!ignorePaths.contains(f.getName())) {
                        try {
                            DirectoryComponent sub = createDirectoryGraph(output, f.getPath(), ignorePaths);
                            subDirectories.add(sub);
                        } catch (NullPointerException e) {
                            System.out.println("NullPointerException in DirectoryFactory"); //TODO Log
                        }
                    }
                }
//                if (fileTypeMatchesLanguage(f)) {
                    files.add(f.getAbsolutePath());
//                }
            });
            output.setFiles(files);
            output.setSubComponents(subDirectories.stream().map(e -> (Component) e).collect(Collectors.toList()));
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
//    private boolean fileTypeMatchesLanguage(File f) {
//        if (lang.equalsIgnoreCase("java")) {
//            return f.getName().endsWith(".java");
//        }
//        return false;
//    }

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
        output.setPath(path);
        output.setInstanceType(InstanceType.DIRECTORYCOMPONENT);
        return output;
    }
}
