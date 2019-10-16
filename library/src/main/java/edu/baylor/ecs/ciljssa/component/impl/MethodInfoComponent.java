package edu.baylor.ecs.ciljssa.component.impl;

import com.fasterxml.jackson.annotation.*;
import edu.baylor.ecs.ciljssa.component.IComponent;
import edu.baylor.ecs.ciljssa.model.InstanceType;
import edu.baylor.ecs.ciljssa.model.MethodParam;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class MethodInfoComponent implements IComponent {

    private Long id;

    private IComponent parentComponent;
    @JsonIgnore
    private String rawSource;
    @JsonIgnore
    private InstanceType instanceType;
    private String path;

    private String accessor;
    @JsonProperty(value = "method_name")
    private String methodName;
    @JsonProperty(value = "return_type")
    private String returnType;
    @JsonProperty(value = "parameters")
    private List<MethodParam> methodParams;
    @JsonProperty(value = "static_method")
    private boolean staticMethod; // TODO: Abstract?
    @JsonProperty(value = "subroutines")
    private List<MethodInfoComponent> subMethods;
    private List<AnnotationComponent> annotations;
    @JsonProperty(value = "package_name")
    private String packageName;

    public MethodInfoComponent() {
        this .instanceType = InstanceType.METHODCOMPONENT;
    }

    @Override
    public String getPathToComponent() {
        return parentComponent.getPathToComponent();
    }

    @Override
    public void setPathToComponent(String path) {
        this.path = path + "::MethodDeclaration::" + this.methodName;
    }

    @Override
    public String getPackageName() {
        return parentComponent.getPackageName();
    }

    @Override
    public void setPackageName(String name) {
        this.packageName = name;
    }

    public String getSourceAsString() {
        return rawSource;
    }

    @Override
    public String getInstanceName() {
        return methodName;
    }

    @Override
    public void setInstanceName(String name) {
        this.methodName = name;
    }

    @Override
    public InstanceType getInstanceType() {
        return this.instanceType;
    }

    @Override
    public void setInstanceType(InstanceType type) {
        this.instanceType = type;
    }

    @Override
    public void setParentComponent(IComponent component) {
        this.parentComponent = component;
    }
}
