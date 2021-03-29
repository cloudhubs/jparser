package edu.baylor.ecs.cloudhubs.jparser.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AnnotationValuePair {

    private String key;
    private String value;

    public AnnotationValuePair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return key + "->" + value;
    }

}
