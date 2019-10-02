package edu.baylor.ecs.ciljssa.wrappers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.baylor.ecs.ciljssa.model.MethodParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MethodInfoWrapper {

    @JsonIgnore
    private ComponentWrapper parentComponent;

    @JsonProperty(value = "method_name")
    private String methodName;
    @JsonProperty(value = "return_type")
    private String returnType;
    @JsonProperty(value = "parameters")
    private List<MethodParam> methodParams;
    @JsonProperty(value = "static_method")
    private boolean staticMethod;
    @JsonProperty(value = "subroutines")
    private List<MethodInfoWrapper> subMethods;
    private String accessor;
    private List<AnnotationWrapper> annotations;

}
