package edu.baylor.ecs.ciljssa.component.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.baylor.ecs.ciljssa.component.Component;
import edu.baylor.ecs.ciljssa.model.AccessorType;
import edu.baylor.ecs.ciljssa.visitor.IComponentVisitor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ClassField extends Component {

    private List<Component> annotations;
    /**
     * This field is a list because you may declare multiple variables on one line (int x, y, z)
     */
    private List<String> variables;
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

    @Override
    public void accept(IComponentVisitor visitor) {

    }
}
