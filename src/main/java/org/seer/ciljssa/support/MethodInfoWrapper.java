package org.seer.ciljssa.support;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MethodInfoWrapper {

    @JsonProperty(value = "method_name")
    private String methodName;
    @JsonProperty(value = "return_type")
    private String returnType;
    private String accessor;
    @JsonProperty(value = "static_method")
    private boolean staticMethod;
    @JsonProperty(value = "parameters")
    private String[] methodParams;
    private String[] annotations;
}
