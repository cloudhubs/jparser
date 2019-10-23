package edu.baylor.ecs.ciljssa.component.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.baylor.ecs.ciljssa.component.Component;
import lombok.Data;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class DirectoryComponent extends Component {

    private List<File> files;
    private List<DirectoryComponent> subDirectories;
    @JsonIgnore
    private DirectoryComponent parentDirectory;
    private int numFiles;

    public boolean hasSubDirectories;

    public DirectoryComponent(String path) {
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
        List<Component> output = subDirectories.stream().map(e -> (Component) e).collect(Collectors.toList()); //TODO: Hacky?
        return output;
    }

    @Override
    public void setSubComponents(List<Component> subComponents) {
        List<DirectoryComponent> output = subComponents.stream().map(e -> (DirectoryComponent) e).collect(Collectors.toList()); //TODO: Hacky?
        this.subDirectories = output;
    }

}
