package edu.baylor.ecs.ciljssa.component.impl;

import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.model.InstanceType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * This class should become deprecated in future versions. We should just map sub components to a single list.
 *
 * As it stands now, ClassComponents contain only 1 sub component, which is a meta sub component.
 */
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
    public void setInstanceName(String name) {
        this.parent.setInstanceName(name);
    }

    @Override
    public InstanceType getInstanceType() {
        return InstanceType.META;
    }
}
