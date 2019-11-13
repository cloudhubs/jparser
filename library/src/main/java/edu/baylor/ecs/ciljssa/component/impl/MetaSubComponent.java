package edu.baylor.ecs.ciljssa.component.impl;

import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.model.InstanceType;
import edu.baylor.ecs.ciljssa.visitor.IComponentVisitor;
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

    /**
     * A MetaSubComponent is an arbitrary construct and must always exist in the same package as its parent.
     */
    @Override
    public String getPath() {
        return parent.getPath();
    }

    /**
     * A MetaSubComponent is an arbitrary construct and must always exist in the same package as its parent.
     */
    @Override
    public void setPath(String path) {
        this.parent.setPath(path);
    }

    /**
     * A MetaSubComponent is an arbitrary construct and must always exist in the same package as its parent.
     */
    @Override
    public String getPackageName() {
        return this.parent.getPackageName();
    }

    /**
     * A MetaSubComponent is an arbitrary construct and must always exist in the same package as its parent.
     * @param name
     */
    @Override
    public void setPackageName(String name) {
        this.parent.setPackageName(name);
    }

    @Override
    public void setInstanceName(String name) {
        this.instanceName = name;
    }

    @Override
    public InstanceType getInstanceType() {
        return InstanceType.META;
    }

    @Override
    public void accept(IComponentVisitor visitor) {

    }
}
