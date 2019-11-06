package edu.baylor.ecs.ciljssa.component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.baylor.ecs.ciljssa.component.impl.DirectoryComponent;
import edu.baylor.ecs.ciljssa.model.InstanceType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Component {

    @JsonIgnore
    protected Component parent;

    protected String path;
    @JsonProperty(value = "package_name")
    protected String packageName;
    protected String instanceName;
    protected InstanceType instanceType;
    protected List<Component> subComponents;

    public boolean hasMultipleSubComponents() {
        return this.subComponents.size() > 1;
    }

    /**
     * Use for when there is known only one sub component rather than a list.
     * @return The sub component, null if there is more than one.
     */
    @JsonIgnore
    public Component getSubComponent() {
        if (!hasMultipleSubComponents()) {
            return this.subComponents.get(0);
        } else return null;
    }

    public void addSubComponent(Component subComponent) {
        if (this.subComponents == null)
            this.subComponents = new ArrayList<>();
        this.subComponents.add(subComponent);
    }
}
