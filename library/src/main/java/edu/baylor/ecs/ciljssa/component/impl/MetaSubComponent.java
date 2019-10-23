package edu.baylor.ecs.ciljssa.component.impl;

import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.model.InstanceType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MetaSubComponent extends Component {

    private List<Component> methodInfoComponents;
    private List<AnnotationComponent> annotationComponents;
    private List<Component> constructorComponents;
    private List<ClassComponent> subClassComponents;

    @Override
    public String getPath() {
        return parent.getPath();
    }

    @Override
    public void setPath(String path) {
        this.parent.setPath(path);
    }

    @Override
    public String getPackageName() {
        return this.parent.getPackageName();
    }

    @Override
    public void setPackageName(String name) {
        this.parent.setPackageName(name);
    }

    @Override
    public String getInstanceName() {
        return this.parent.getInstanceName() + "::MetaSubComponent";
    }

    @Override
    public void setInstanceName(String name) {
        this.parent.setInstanceName(name);
    }

    @Override
    public InstanceType getInstanceType() {
        return InstanceType.META;
    }
}
