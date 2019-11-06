package edu.baylor.ecs.ciljssa.component.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.baylor.ecs.ciljssa.component.Component;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class DirectoryComponent extends Component {

    private String language;
    private List<File> files;
    private List<DirectoryComponent> subDirectories;
    @JsonIgnore
    private DirectoryComponent parentDirectory;
    private int numFiles;

    public boolean hasSubDirectories;

    public DirectoryComponent() {
        this.files = new ArrayList<>();
        this.subDirectories = new ArrayList<>();
        this.parentDirectory = null;
        this.numFiles = 0;
        this.path = "";
    }

    public DirectoryComponent(String path) {
        this();
        this.path = path;
    }

    public int getNumFiles() {
        return files.size();
    }

    public void setNumFiles() {
    }

    public void setFiles(List<File> files) {
        this.files = files;
        this.numFiles = files.size();
    }

    @Override
    @JsonIgnore
    public List<Component> getSubComponents() {
        return subDirectories.stream().map(e -> (Component) e).collect(Collectors.toList());
    }

    @Override
    public void setSubComponents(List<Component> subComponents) {
        this.subDirectories = subComponents.stream().map(e -> (DirectoryComponent) e).collect(Collectors.toList());
    }

    public void addSubDirectory(DirectoryComponent subComponent) {
        if (this.subDirectories == null)
            this.subDirectories = new ArrayList<>();
        this.subDirectories.add(subComponent);
        this.addSubComponent(subComponent);
    }
}
