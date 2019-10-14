package edu.baylor.ecs.ciljssa.component.impl;

import edu.baylor.ecs.ciljssa.component.IComponent;
import edu.baylor.ecs.ciljssa.model.InstanceType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MetaSubComponent implements IComponent {

    private IComponent parentComponent;
    private List<MethodInfoComponent> methodInfoComponents;
    private List<AnnotationComponent> annotationComponents;
    private List<MethodInfoComponent> constructorComponents;
    private List<ClassComponent> subClassComponents;

    @Override
    public String getPathToComponent() {
        return parentComponent.getPathToComponent();
    }

    @Override
    public void setPathToComponent(String path) {
        this.parentComponent.setPathToComponent(path);
    }

    @Override
    public String getPackageName() {
        return this.parentComponent.getPackageName();
    }

    @Override
    public void setPackageName(String name) {
        this.parentComponent.setPackageName(name);
    }

    @Override
    public String getInstanceName() {
        return this.parentComponent.getInstanceName() + "::MetaSubComponent";
    }

    @Override
    public void setInstanceName(String name) {
        this.parentComponent.setInstanceName(name);
    }

    @Override
    public InstanceType getInstanceType() {
        return InstanceType.META;
    }

    @Override
    public void setInstanceType(InstanceType type) {
        // Do nothing
    }
}
