package edu.baylor.ecs.ciljssa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MethodParam {

    private String annotation; // TODO: Change to AnnotationWrapper
    @JsonProperty(value = "parameter_type")
    private String parameterType;
    @JsonProperty(value = "parameter_name")
    private String parameterName;

}
