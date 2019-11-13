package edu.baylor.ecs.ciljssa.component.impl;

import com.fasterxml.jackson.annotation.*;
import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.model.AccessorType;
import edu.baylor.ecs.ciljssa.model.InstanceType;
import edu.baylor.ecs.ciljssa.visitor.IComponentVisitor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class MethodInfoComponent extends Component {

    @JsonIgnore
    private String rawSource;
    @JsonIgnore
    private List<String> statements; //

    private Long id;
    private AccessorType accessor; //
    @JsonProperty(value = "method_name")
    private String methodName; //
    @JsonProperty(value = "return_type")
    private String returnType; //
    @JsonProperty(value = "parameters")
    private List<MethodParamComponent> methodParams; //
    @JsonProperty(value = "static_method")
    private boolean staticMethod; //
    @JsonProperty(value = "abstract_method")
    private boolean abstractMethod; //
    @JsonProperty(value = "subroutines")
    private List<MethodInfoComponent> subMethods; //
    private List<AnnotationComponent> annotations; //

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

    @Override
    public void accept(IComponentVisitor visitor) {
        visitor.visit(this);
        for (Component e : this.subComponents) {
            e.accept(visitor);
        }
    }
}
