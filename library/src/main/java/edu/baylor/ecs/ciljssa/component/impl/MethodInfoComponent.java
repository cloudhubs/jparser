package edu.baylor.ecs.ciljssa.component.impl;

import com.fasterxml.jackson.annotation.*;
import edu.baylor.ecs.ciljssa.component.IComponent;
import edu.baylor.ecs.ciljssa.component.IContainerComponent;
import edu.baylor.ecs.ciljssa.component.InstanceType;
import edu.baylor.ecs.ciljssa.model.MethodParam;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class MethodInfoComponent implements IComponent {

    private Long id;

    private IContainerComponent parentComponent;
    @JsonIgnore
    private String rawSource;
    @JsonIgnore
    private final InstanceType instanceType = InstanceType.METHODCOMPONENT;

    private String accessor;
    @JsonProperty(value = "method_name")
    private String methodName;
    @JsonProperty(value = "return_type")
    private String returnType;
    @JsonProperty(value = "parameters")
    private List<MethodParam> methodParams;
    @JsonProperty(value = "static_method")
    private boolean staticMethod;
    @JsonProperty(value = "subroutines")
    private List<MethodInfoComponent> subMethods;
    private List<AnnotationComponent> annotations;

    @Override
    public String getPathToComponent() {
        return parentComponent.getPathToComponent();
    }

    @Override
    public String getPackageName() {
        return parentComponent.getPackageName();
    }

    @Override
    public String getSourceAsString() {
        return rawSource;
    }

    @Override
    public String getInstanceName() {
        return methodName;
    }

    @Override
    public InstanceType getInstanceType() {
        return this.instanceType;
    }
}
