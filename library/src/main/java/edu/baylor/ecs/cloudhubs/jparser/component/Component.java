package edu.baylor.ecs.cloudhubs.jparser.component;

import com.fasterxml.jackson.annotation.*;
import edu.baylor.ecs.cloudhubs.jparser.component.context.AnalysisContext;
import edu.baylor.ecs.cloudhubs.jparser.component.impl.*;
import edu.baylor.ecs.cloudhubs.jparser.model.InstanceType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "instanceType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ModuleComponent.class, name="MODULECOMPONENT"),
    @JsonSubTypes.Type(value = AnnotationComponent.class, name="ANNOTATIONCOMPONENT"),
    @JsonSubTypes.Type(value = ClassComponent.class, name="CLASSCOMPONENT"),
    @JsonSubTypes.Type(value = FieldComponent.class, name="FIELDCOMPONENT"),
    @JsonSubTypes.Type(value = InterfaceComponent.class, name="INTERFACECOMPONENT"),
    @JsonSubTypes.Type(value = MethodInfoComponent.class, name="METHODCOMPONENT"),
    @JsonSubTypes.Type(value = MethodParamComponent.class, name="METHODPARAMCOMPONENT"),
    @JsonSubTypes.Type(value = AnalysisContext.class, name="ANALYSISCOMPONENT"),
})
public abstract class Component implements IComponent {

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

    public FieldComponent asFieldComponent() {
        if (this instanceof FieldComponent) {
            return (FieldComponent) this;
        } else {
            return null;
        }
    }

    public DirectoryComponent asDirectoryComponent() {
        if (this instanceof DirectoryComponent) {
            return (DirectoryComponent) this;
        } else {
            return null;
        }
    }

    public AnnotationComponent asAnnotationComponent() {
        if (this instanceof AnnotationComponent) {
            return (AnnotationComponent) this;
        } else {
            return null;
        }
    }

    public ClassComponent asClassComponent() {
        if (this instanceof ClassComponent) {
            return (ClassComponent) this;
        } else {
            return null;
        }
    }

    public InterfaceComponent asInterfaceComponent() {
        if (this instanceof InterfaceComponent) {
            return (InterfaceComponent) this;
        } else {
            return null;
        }
    }

    public MethodInfoComponent asMethodInfoComponent() {
        if (this instanceof MethodInfoComponent) {
            return (MethodInfoComponent) this;
        } else {
            return null;
        }
    }

}
