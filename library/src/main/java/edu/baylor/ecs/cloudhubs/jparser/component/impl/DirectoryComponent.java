package edu.baylor.ecs.cloudhubs.jparser.component.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.baylor.ecs.cloudhubs.jparser.component.Component;
import edu.baylor.ecs.cloudhubs.jparser.visitor.IComponentVisitor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class DirectoryComponent extends Component {
    private List<String> files;
    private List<DirectoryComponent> subDirectories;

    public DirectoryComponent() {
        this.files = new ArrayList<>();
        this.subDirectories = new ArrayList<>();
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

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public void addFile(String file) {
        this.files.add(file);
    }

    @JsonIgnore
    public List<Component> getSubComponents() {
        return subDirectories.stream().map(e -> (Component) e).collect(Collectors.toList());
    }

    public void setSubComponents(List<Component> subComponents) {
        this.subDirectories = subComponents.stream().map(e -> (DirectoryComponent) e).collect(Collectors.toList());
    }

    public void addSubDirectory(DirectoryComponent subComponent) {
        if (this.subDirectories == null)
            this.subDirectories = new ArrayList<>();
        this.subDirectories.add(subComponent);
        this.addSubComponent(subComponent);
    }

    @Override
    public void accept(IComponentVisitor visitor) {

    }
}
