package edu.baylor.ecs.ciljssa.component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.baylor.ecs.ciljssa.model.InstanceType;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Component {

    protected String path;
    protected String packageName;
    protected String instanceName;
    @JsonIgnore
    protected Component parent;
    protected InstanceType instanceType;
    protected List<Component> subComponents;

    public boolean hasMultipleSubComponents() {
        return this.subComponents.size() > 1;
    }

    /**
     * Use for when there is known only one sub component rather than a list.
     * @return The sub component, null if there is more than one.
     */
    public Component getSubComponent() {
        if (!hasMultipleSubComponents()) {
            return this.subComponents.get(0);
        } else return null;
    }

}
