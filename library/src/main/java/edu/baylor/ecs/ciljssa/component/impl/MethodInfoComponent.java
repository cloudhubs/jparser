package edu.baylor.ecs.ciljssa.component.impl;

import com.fasterxml.jackson.annotation.*;
import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.model.AccessorType;
import edu.baylor.ecs.ciljssa.model.InstanceType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
//@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class MethodInfoComponent extends Component {

    private Long id;

    @JsonIgnore
    private String rawSource;

    private AccessorType accessor;
    @JsonProperty(value = "method_name")
    private String methodName;
    @JsonProperty(value = "return_type")
    private String returnType;
    @JsonProperty(value = "parameters")
    private List<MethodParam> methodParams;
    @JsonProperty(value = "static_method")
    private boolean staticMethod;
    @JsonProperty(value = "abstract_method")
    private boolean abstractMethod;
    @JsonProperty(value = "subroutines")
    private List<MethodInfoComponent> subMethods;
    private List<AnnotationComponent> annotations;

    public MethodInfoComponent() {
        this.instanceType = InstanceType.METHODCOMPONENT;
    }

    @Override
    public String getPath() {
        if (parent != null) {
            if (parent.getPath() != null)
                return parent.getPath() + "::MethodDeclaration::" + this.methodName; //TODO: Add line numbers?
            else
                return null;
        } else {
            return null;
        }
    }

    @Override
    public String getPackageName() {
        if (parent != null) {
            if (parent.getPackageName() != null)
                return parent.getPackageName();
            else
                return null;
        } else {
            return null;
        }
    }

    public String getSourceAsString() {
        return rawSource;
    }

}
