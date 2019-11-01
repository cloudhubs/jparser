package edu.baylor.ecs.ciljssa.component.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.model.AccessorType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FieldComponent extends Component {

    private AccessorType accessor;
    private String fieldName;
    @JsonProperty(value = "static")
    private boolean staticField;


    @Override
    public String getInstanceName() {
        return fieldName;
    }

}
