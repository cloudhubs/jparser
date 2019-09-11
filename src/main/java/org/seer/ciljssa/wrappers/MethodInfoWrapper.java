package org.seer.ciljssa.wrappers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MethodInfoWrapper {

    @JsonProperty(value = "method_name")
    private String methodName;
    @JsonProperty(value = "return_type")
    private String returnType;
    @JsonProperty(value = "parameters")
    private List<String[]> methodParams;
    @JsonProperty(value = "static_method")
    private boolean staticMethod;
    private String accessor;
    private List<AnnotationWrapper> annotations;

}
