package edu.baylor.ecs.ciljssa.component.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.model.AccessorType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassField extends Component {

    private AnnotationComponent annotation;
    @JsonProperty(value = "field_name")
    private String fieldName;
    private AccessorType accessor;
    @JsonProperty(value = "static")
    private boolean staticField;
    @JsonProperty(value = "final")
    private boolean finalField;
    private Class<?> type;
    @JsonProperty(value = "default_value_string")
    private String stringifiedDefaultValue;

}
