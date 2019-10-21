package edu.baylor.ecs.ciljssa.component.impl;

import com.fasterxml.jackson.annotation.*;
import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.component.IComponent;
import edu.baylor.ecs.ciljssa.model.InstanceType;
import edu.baylor.ecs.ciljssa.model.MethodParam;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class MethodInfoComponent extends Component {

    private Long id;

    @JsonIgnore
    private String rawSource;

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

    public MethodInfoComponent() {
        this.instanceType = InstanceType.METHODCOMPONENT;
    }

    @Override
    public String getPath() {
        return parent.getPath() + "::MethodDeclaration::" + this.methodName; //TODO: Add line numbers?
    }

    @Override
    public String getPackageName() {
        return parent.getPackageName();
    }

    public String getSourceAsString() {
        return rawSource;
    }

}
